package com.hustunique.jianguo.driclient.presenters.strategy;

import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleUserService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 5/5/16.
 * Strategy for loading shots by user id.
 */
public class GetShotByIdStrategy implements ILoadShotStrategy {

    private final String id;
    private final Map<String, String> params;

    public GetShotByIdStrategy(String id, Map<String, String> params) {
        this.id = id;
        this.params = params;
    }

    @Override
    public Observable<List<Shots>> loadData() {
        return ApiServiceFactory.createService(DribbbleUserService.class)
                .getShots(id, params);
    }

    @Override
    public Observable<List<Shots>> loadMore() {
        return null;
    }

    @Override
    public int getLoadingCount() {
        return 0;
    }
}
