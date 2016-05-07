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
import com.hustunique.jianguo.driclient.views.BucketInShotListView;

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
 * Show buckets by shots' id
 */
public class ShotBucketPresenter extends BasePresenter<List<Buckets>, BucketInShotListView> {
    private boolean isLoadingData = false;
    protected static final String SHOT = "shot";
    private Shots mShot;

    public ShotBucketPresenter() {

    }


    public ShotBucketPresenter(Intent intent) {
        mShot = (Shots) intent.getSerializableExtra(SHOT);
    }

    @Override
    protected void updateView() {
        if (model.size() == 0) view().showEmpty();
        else view().showData(model);
        if (mShot != null) {
            view().setTitle(String.format(AppData.getString(R.string.comments_subtitle)
                    , mShot.getTitle()
                    , mShot.getUser().getName()));
        }
    }

    @Override
    public void bindView(@NonNull BucketInShotListView view) {
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

    public void addToBucket(final Buckets bucket) {
        if (mShot == null) {
            Log.e("driclient", "shot must given!");
            return;

        }

        ResponseBodyFactory.createService(DribbbleBucketsService.class)
                .putShotInBucket(bucket.getId(), mShot.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<retrofit2.Response<ResponseBody>>() {
                    @Override
                    public void call(retrofit2.Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 204) {
                            view().addToBucket(bucket);
                        } else {
                            Log.e("driclient", "add to bucket failed " + responseBodyResponse.code());
                        }
                    }
                });
    }
}
