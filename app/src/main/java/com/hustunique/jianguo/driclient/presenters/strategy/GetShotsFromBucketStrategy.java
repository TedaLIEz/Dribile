package com.hustunique.jianguo.driclient.presenters.strategy;

import com.hustunique.jianguo.driclient.models.Buckets;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleBucketsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 5/5/16.
 * Strategy for loading shots from target buckets.
 */
public class GetShotsFromBucketStrategy implements ILoadShotStrategy {

    private final Buckets mBucket;
    private final Map<String, String> params;

    public GetShotsFromBucketStrategy(Buckets buckets, Map<String, String> params) {
        mBucket = buckets;
        this.params = params;
    }

    @Override
    public Observable<List<Shots>> loadData() {
        return ApiServiceFactory.createService(DribbbleBucketsService.class)
                .getShotsFromBuckets(mBucket.getId(), params);
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
