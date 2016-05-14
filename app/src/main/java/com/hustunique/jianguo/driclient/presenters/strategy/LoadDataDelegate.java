package com.hustunique.jianguo.driclient.presenters.strategy;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JianGuo on 5/5/16.
 * Delegate for loading data
 */
public class LoadDataDelegate<T> {
    public static final String SORT_COMMENTS = "comments";
    public static final String SORT_RECENT = "recent";
    public static final String SORT_VIEWS = "views";
    Map<String, String> params;
    protected static final String ARG_SORT_TYPE = "sort";
    private String mSortType;
    private int COUNT_PER_PAGE = 20;
    private int page = 1;



    private ILoadDataStrategy<T> mLoadStrategy;
    private ICacheDataStrategy<T> mCacheStrategy;

    public LoadDataDelegate() {
        generateDefaultParams();
    }


    public void setLoadStrategy(ILoadDataStrategy<T> strategy) {
        mLoadStrategy = strategy;
    }

    public void setCacheStrategy(ICacheDataStrategy<T> cacheStrategy) {
        this.mCacheStrategy = cacheStrategy;
    }

    @StringDef({SORT_COMMENTS, SORT_RECENT, SORT_VIEWS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SortType {
    }

    public void setSortType(@SortType String sortType) {
        mSortType = sortType;
        params.put(ARG_SORT_TYPE, mSortType);
    }

    public void setPerPage(int perPage) {
        COUNT_PER_PAGE = perPage;
        params.put("per_page", Integer.toString(COUNT_PER_PAGE));
    }

    private void generateDefaultParams() {
        params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put(ARG_SORT_TYPE, SORT_VIEWS);
        params.put("per_page", String.valueOf(COUNT_PER_PAGE));
    }

    public Observable<List<T>> loadData() {
        page = 1;
        return loadData(page);
    }

    public Observable<List<T>> loadMore() {
        page++;
        return loadData(page);
    }

    public Observable<List<T>> loadData(int page) {
        params.put("page", String.valueOf(page));
        return mLoadStrategy.loadData(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<List<T>> loadFromDB() {
        if (mCacheStrategy != null) {
            return Observable.create(new Observable.OnSubscribe<List<T>>() {
                @Override
                public void call(Subscriber<? super List<T>> subscriber) {
                    subscriber.onNext(mCacheStrategy.loadFromDB());
                    subscriber.onCompleted();
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return null;
    }

    public Observable<Boolean> cache(final List<T> datas) {
        final List<T> rst = Collections.synchronizedList(datas);
        if (mCacheStrategy != null) {

            return Observable.create(new Observable.OnSubscribe<Boolean>() {

                @Override
                public void call(Subscriber<? super Boolean> subscriber) {
                    subscriber.onNext(mCacheStrategy.cache(rst));

                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return null;
    }

    public int getLoadTotal() {
        return page * COUNT_PER_PAGE;
    }
}
