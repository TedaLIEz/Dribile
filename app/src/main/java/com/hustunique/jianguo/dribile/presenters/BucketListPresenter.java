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

import android.app.usage.NetworkStats;

import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.presenters.strategy.GetMyBucketStrategy;
import com.hustunique.jianguo.dribile.service.DribbbleBucketsService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.dribile.service.factories.ResponseBodyFactory;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.utils.ObservableTransformer;
import com.hustunique.jianguo.dribile.views.BucketListView;

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
    private static final String TAG = "BucketListPresenter";

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
                            Logger.e(TAG, "delete bucket " + bucket.getId() + " failed");
                        }
                    }
                });
    }


    @Override
    public void refresh() {
        mLoadDel.loadData()
                .compose(new ObservableTransformer<List<Buckets>>())
                .subscribe(new LoadingListSubscriber() {
            @Override
            public void onNext(List<Buckets> bucketses) {
                setModel(bucketses);
            }
        });
    }
}
