package com.hustunique.jianguo.dribile.presenters.strategy;

import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.service.DribbbleBucketsService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 5/5/16.
 * Strategy for loading shots from target buckets.
 */
public class GetShotsFromBucketStrategy implements ILoadListDataStrategy<Shots> {

    private final Buckets mBucket;

    public GetShotsFromBucketStrategy(Buckets buckets) {
        mBucket = buckets;
    }

    @Override
    public Observable<List<Shots>> loadData(Map<String, String> params) {
        return ApiServiceFactory.createService(DribbbleBucketsService.class)
                .getShotsFromBuckets(mBucket.getId(), params);
    }
}
