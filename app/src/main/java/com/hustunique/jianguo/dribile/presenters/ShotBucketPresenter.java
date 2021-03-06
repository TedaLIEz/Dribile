/*
 * Copyright (c) 2016.  TedaLIEz <aliezted@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hustunique.jianguo.dribile.presenters;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.service.endpoint.DribbbleBucketsService;
import com.hustunique.jianguo.dribile.service.endpoint.DribbbleUserService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.dribile.service.factories.ResponseBodyFactory;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.utils.ObservableTransformer;
import com.hustunique.jianguo.dribile.utils.Utils;
import com.hustunique.jianguo.dribile.views.BucketInShotListView;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by JianGuo on 5/3/16.
 * Show buckets by shots' id
 */
public class ShotBucketPresenter extends BasePresenter<List<Buckets>, BucketInShotListView> {
    private static final String TAG = "ShotBucketPresenter";
    private boolean isLoadingData = false;
    private Shots mShot;

    public ShotBucketPresenter() {

    }


    public ShotBucketPresenter(Intent intent) {
        mShot = (Shots) intent.getSerializableExtra(Utils.EXTRA_SHOT);
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
                .compose(new ObservableTransformer<List<Buckets>>())
                .subscribe(new Subscriber<List<Buckets>>() {
                    @Override
                    public void onCompleted() {
                        Logger.i(TAG, "load buckets successfully");
                        isLoadingData = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            view().onError(exception);
                        }
                        Logger.wtf(TAG, e);
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
                .compose(new ObservableTransformer<Response<ResponseBody>>())
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
                            Logger.e(TAG ,"delete bucket " + bucket.getId() + " failed");
                        }
                    }
                });
    }

    public void createBucket(String name, String description) {
        ApiServiceFactory.createService(DribbbleBucketsService.class)
                .createBucket(name, description)
                .compose(new ObservableTransformer<Buckets>())
                .subscribe(new Subscriber<Buckets>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.wtf(TAG, e);
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
            Logger.e(TAG, "shot must given!");
            return;

        }

        ResponseBodyFactory.createService(DribbbleBucketsService.class)
                .putShotInBucket(bucket.getId(), mShot.getId())
                .compose(new ObservableTransformer<Response<ResponseBody>>())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 204) {
                            view().addToBucket(bucket);
                        } else {
                            Logger.e(TAG, "add to bucket failed " + responseBodyResponse.code());
                        }
                    }
                });
    }
}
