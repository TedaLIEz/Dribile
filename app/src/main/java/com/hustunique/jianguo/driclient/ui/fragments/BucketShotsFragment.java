package com.hustunique.jianguo.driclient.ui.fragments;

import android.os.Bundle;

import com.hustunique.jianguo.driclient.bean.Buckets;
import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleBucketsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 4/21/16.
 * Fragment for listing shots in the bucket
 */
public class BucketShotsFragment extends BaseShotListFragment {
    public static final String BUCKET = "bucket";

    private Buckets mBucket;

    public BucketShotsFragment() {

    }

    public static BucketShotsFragment newInstance(@SortType String sortType, Buckets buckets) {
        BucketShotsFragment bucketShotsFragment = new BucketShotsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BucketShotsFragment.ARG_SORT_TYPE, sortType);
        bundle.putSerializable(BUCKET, buckets);
        bucketShotsFragment.setArguments(bundle);
        return bucketShotsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBucket = (Buckets) getArguments().getSerializable(BUCKET);
    }

    @Override
    public Observable<List<Shots>> loadData(Map<String, String> params) {
        return ApiServiceFactory.createService(DribbbleBucketsService.class)
                .getShotsFromBuckets(mBucket.getId());
    }

    @Override
    public void onFabClick() {

    }
}
