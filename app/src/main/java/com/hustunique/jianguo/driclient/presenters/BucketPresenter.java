package com.hustunique.jianguo.driclient.presenters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.models.Buckets;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleBucketsService;
import com.hustunique.jianguo.driclient.service.DribbbleUserService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.driclient.service.factories.ResponseBodyFactory;
import com.hustunique.jianguo.driclient.views.BucketListView;
import com.hustunique.jianguo.driclient.views.BucketView;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by JianGuo on 5/3/16.
 */
public class BucketPresenter extends BasePresenter<List<Buckets>, BucketView> {
    private boolean isLoadingData = false;
    protected static final String SHOT = "shot";
    private Shots mShot;

    public BucketPresenter() {

    }


    @Override
    protected void updateView() {
        if (model.size() == 0) view().showEmpty();
        else view().showData(model);
    }

    @Override
    public void bindView(@NonNull BucketView view) {
        super.bindView(view);
        if (model == null && !isLoadingData) {
            view.showLoading();
            loadData();
        }
    }

    private void loadData() {
        isLoadingData = true;
        ApiServiceFactory.createService(DribbbleUserService.class)
                .getAuthBuckets()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Buckets>>() {
                    @Override
                    public void onCompleted() {
                        Log.i("driclient", "load buckets successfully");
                        isLoadingData = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            view().onError(exception);
                        }
                        Log.wtf("driclient", e);
                    }

                    @Override
                    public void onNext(List<Buckets> bucketses) {
                        setModel(bucketses);
                    }
                });
    }


    public void createBucket(String name, String description) {
        ApiServiceFactory.createService(DribbbleBucketsService.class)
                .createBucket(name, description)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Buckets>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.wtf("driclient", e);
                    }

                    @Override
                    public void onNext(Buckets buckets) {
                        view().createBucket(buckets);
                        model.add(buckets);
                        if (model.size() != 0) {
                            view().showData(model);
                        }
                    }
                });
    }



    public void deleteBucket(final Buckets bucket) {
        ResponseBodyFactory.createService(DribbbleBucketsService.class)
                .deleteBucket(bucket.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(retrofit2.Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 204) {
                            view().removeBucket(bucket);
                            model.remove(bucket);
                            if (model.size() == 0) {
                                view().showEmpty();
                            }
                        } else {
                            Log.e("driclient" ,"delete bucket " + bucket.getId() + " failed");
                        }
                    }
                });
    }


}
