package com.hustunique.jianguo.driclient.presenters;

import android.util.Log;

import com.hustunique.jianguo.driclient.models.Buckets;
import com.hustunique.jianguo.driclient.presenters.strategy.GetMyBucketStrategy;
import com.hustunique.jianguo.driclient.service.DribbbleBucketsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.driclient.service.factories.ResponseBodyFactory;
import com.hustunique.jianguo.driclient.views.BucketListView;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by JianGuo on 5/3/16.
 * Presenter for loading buckets of the auth user.
 */
public class BucketListPresenter extends BaseListPresenter<Buckets, BucketListView> {


    public BucketListPresenter() {
        super();
        mLoadDel.setLoadStrategy(new GetMyBucketStrategy());
    }

    @Override
    public void getData() {
        refresh();
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
                            Log.e("driclient", "delete bucket " + bucket.getId() + " failed");
                        }
                    }
                });
    }


    @Override
    public void refresh() {
        mLoadDel.loadData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadingListSubscriber() {
            @Override
            public void onNext(List<Buckets> bucketses) {
                setModel(bucketses);
            }
        });
    }
}
