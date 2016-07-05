package com.hustunique.jianguo.dribile.presenters;

import android.util.Log;

import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.presenters.strategy.ICacheDataStrategy;
import com.hustunique.jianguo.dribile.presenters.strategy.ILoadListDataStrategy;
import com.hustunique.jianguo.dribile.views.ILoadListView;
import com.hustunique.jianguo.dribile.views.LikeListView;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by JianGuo on 5/4/16.
 * Presenter for listing shots.
 */
public class LikeListPresenter extends BaseListPresenter<Shots, LikeListView> {

    public LikeListPresenter() {
        super();
    }

    public void setLoadStrategy(ILoadListDataStrategy<Shots> loadStrategy) {
        mLoadDel.setLoadStrategy(loadStrategy);
    }

    public void setCacheStrategy(ICacheDataStrategy<Shots> cacheStrategy) {
        mLoadDel.setCacheStrategy(cacheStrategy);
    }

    public void removeChild(int pos) {
        model.remove(pos);
        view().unlikeShot(pos);
    }

    public int getLoadingCount() {
        return mLoadDel.getLoadTotal();
    }

    @Override
    public void refresh() {
        Log.i("driclient", "load data executed from network");
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
                        Log.e("driclient", "cache new data " + shotses.size());
                        mLoadDel.cacheNew(shotses);
                        rst.onNext(shotses);
                    }
                });
        rst.asObservable().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadingListSubscriber() {
                    @Override
                    public void onNext(List<Shots> shotses) {
                        Log.e("driclient", "load data from refresh " + shotses.size());
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
                            Log.e("driclient", "load from database" + shotses.size());
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
                        Log.e("driclient", "cache loadMore data " + shotses.size());
                        mLoadDel.cacheMore(shotses);
                        rst.onNext(shotses);
                    }
                });
        rst.asObservable().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadingListSubscriber() {
                    @Override
                    public void onNext(List<Shots> shotses) {
                        Log.e("driclient", "load loadMore data " + shotses.size());
                        model.addAll(shotses);
                        view().showData(model);
                    }
                });
    }


}