package com.hustunique.jianguo.driclient.presenters.strategy;

import android.util.Log;

import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleShotsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JianGuo on 5/5/16.
 * Strategy for loading all shots
 */
public class GetAllShotsStrategy implements ILoadShotStrategy {

    public static final String SORT_COMMENTS = "comments";
    public static final String SORT_RECENT = "recent";
    public static final String SORT_VIEWS = "views";
    Map<String, String> params;
    protected static final String ARG_SORT_TYPE = "sort";
    private String mSortType;
    private final int COUNT_PER_PAGE = 20;
    private int page = 1;

    public GetAllShotsStrategy() {
        generateDefaultParams();
    }

    private void generateDefaultParams() {
        params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put(ARG_SORT_TYPE, SORT_VIEWS);
        params.put("per_page", String.valueOf(COUNT_PER_PAGE));
    }


    public void setParams(Map<String, String> params) {
        this.params = params;
    }



    @Override
    public Observable<List<Shots>> loadData() {
        page = 1;
        return loadData(page);
    }

    @Override
    public Observable<List<Shots>> loadMore() {
        page++;
        return loadData(page);
    }

    @Override
    public int getLoadingCount() {
        return page * COUNT_PER_PAGE;
    }

    private Observable<List<Shots>> loadData(int page) {
        Log.e("driclient", "load shots in page " + page);
        params.put("page", String.valueOf(page));
        return ApiServiceFactory.createService(DribbbleShotsService.class)
                .getAllShots(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
