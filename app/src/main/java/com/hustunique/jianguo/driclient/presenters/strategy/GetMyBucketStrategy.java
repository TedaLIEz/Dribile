package com.hustunique.jianguo.driclient.presenters.strategy;

import com.hustunique.jianguo.driclient.models.Buckets;
import com.hustunique.jianguo.driclient.service.DribbbleUserService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 5/5/16.
 */
public class GetMyBucketStrategy implements ILoadListDataStrategy<Buckets> {

    @Override
    public Observable<List<Buckets>> loadData(Map<String, String> params) {
        return ApiServiceFactory.createService(DribbbleUserService.class)
                .getAuthBuckets();
    }
}
