package com.hustunique.jianguo.dribile.presenters.strategy;

import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.service.DribbbleUserService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;

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
