package com.hustunique.jianguo.driclient.presenters.strategy;

import android.support.annotation.IntRange;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
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

    public LoadDataDelegate() {
        generateDefaultParams();
    }


    public void setLoadStrategy(ILoadDataStrategy<T> strategy) {
        mLoadStrategy = strategy;
    }

    @StringDef({SORT_COMMENTS, SORT_RECENT, SORT_VIEWS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SortType {
    }

    public void setSortType(@SortType String sortType) {
        mSortType = sortType;
        params.put(ARG_SORT_TYPE, mSortType);
    }

    public void setPerPage(@IntRange(from = 20, to = 40) int perPage) {
        COUNT_PER_PAGE = perPage;
        params.put("par_page", String.valueOf(COUNT_PER_PAGE));
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

    public int getLoadTotal() {
        return page * COUNT_PER_PAGE;
    }
}