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

import com.hustunique.jianguo.dribile.am.MyAccountManager;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.presenters.strategy.ICacheDataStrategy;
import com.hustunique.jianguo.dribile.presenters.strategy.ILoadListDataStrategy;
import com.hustunique.jianguo.dribile.service.endpoint.DribbbleLikeService;
import com.hustunique.jianguo.dribile.service.factories.ResponseBodyFactory;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.utils.ObservableTransformer;
import com.hustunique.jianguo.dribile.views.LikeListView;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by JianGuo on 5/4/16.
 * Presenter for listing shots.
 */
public class LikeListPresenter extends BaseListPresenter<Shots, LikeListView> {

    private static final String TAG = "LikeListPresenter";
    private Shots unlikeShot;

    public LikeListPresenter() {
        super();
    }

    public void setLoadStrategy(ILoadListDataStrategy<Shots> loadStrategy) {
        mLoadDel.setLoadStrategy(loadStrategy);
    }

    public void setCacheStrategy(ICacheDataStrategy<Shots> cacheStrategy) {
        mLoadDel.setCacheStrategy(cacheStrategy);
    }

    public void removeChild(final int pos) {
        unlikeShot = model.get(pos);
        view().unlikeShot(pos);
        ResponseBodyFactory.createService(DribbbleLikeService.class)
                .unlike(model.get(pos).getId()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 204) {
                            view().showUndo(pos);
                            model.remove(pos);
                            MyAccountManager.updateUser();
                            Logger.i(TAG, "unlike success");
                        } else if (responseBodyResponse.code() == 404) {
                            model.remove(pos);
                        } else {
                            view().restoreShot(pos, unlikeShot);
                            Logger.i(TAG, responseBodyResponse.code() + "");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(TAG, "unlike error");
                        view().restoreShot(pos, unlikeShot);
                    }
                });

    }

    public int getLoadingCount() {
        return mLoadDel.getLoadTotal();
    }

    @Override
    public void refresh() {
        Logger.i(TAG, "load data executed from network");
        final BehaviorSubject<List<Shots>> rst = BehaviorSubject.create();
        mLoadDel.loadData().observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Shots>>() {
                    @Override
                    public void onCompleted() {
                        rst.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        rst.onError(e);
                    }

                    @Override
                    public void onNext(List<Shots> shotses) {
                        Logger.i(TAG, "cache new data " + shotses.size());
                        mLoadDel.cacheNew(shotses);
                        rst.onNext(shotses);
                    }
                });
        rst.asObservable().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadingListSubscriber() {
                    @Override
                    public void onNext(List<Shots> shotses) {
                        Logger.i(TAG, "load data from refresh " + shotses.size());
                        setModel(shotses);
                    }
                });
    }

    @Override
    public void getData() {
        if (mLoadDel.isCached()) {
            mLoadDel.loadFromDB().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Shots>>() {
                        @Override
                        public void onCompleted() {
                            view().showLoading();
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (view() != null) {
                                view().onError(e);
                            }
                        }

                        @Override
                        public void onNext(List<Shots> shotses) {
                            setModel(shotses);
                            Logger.e(TAG, "load from database" + shotses.size());
                        }
                    });
        }

        refresh();
    }


    @Override
    protected void resetState() {
        super.resetState();
        if (model != null) {
            model.clear();
        }
    }

    public void loadMore() {
        view().showLoadingMore();
        final BehaviorSubject<List<Shots>> rst = BehaviorSubject.create();
        mLoadDel.loadMore().observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Shots>>() {
                    @Override
                    public void onCompleted() {
                        rst.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        rst.onError(e);
                    }

                    @Override
                    public void onNext(List<Shots> shotses) {
                        Logger.i(TAG, "cache loadMore data " + shotses.size());
                        mLoadDel.cacheMore(shotses);
                        rst.onNext(shotses);
                    }
                });
        rst.asObservable()
                .compose(new ObservableTransformer<List<Shots>>())
                .subscribe(new LoadingListSubscriber() {
                    @Override
                    public void onNext(List<Shots> shotses) {
                        Logger.i(TAG, "load loadMore data " + shotses.size());
                        model.addAll(shotses);
                        view().showData(model);
                    }
                });
    }


    public void restoreShot(final int pos) {
        view().restoreShot(pos, unlikeShot);
        ResponseBodyFactory.createService(DribbbleLikeService.class).like(unlikeShot.getId()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 201) {
                            MyAccountManager.updateUser();
                        } else {
                            view().unlikeShot(pos);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view().unlikeShot(pos);
                    }
                });
    }
}
