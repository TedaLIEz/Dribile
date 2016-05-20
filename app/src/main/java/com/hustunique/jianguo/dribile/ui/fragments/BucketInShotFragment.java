package com.hustunique.jianguo.dribile.ui.fragments;

import android.os.Bundle;

import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.presenters.strategy.GetShotsFromBucketStrategy;

/**
 * Created by JianGuo on 4/21/16.
 * Fragment for listing shots in the bucket
 */
public class BucketInShotFragment extends BaseShotListFragment {
    public static final String BUCKET = "bucket";

    public BucketInShotFragment() {

    }

    public static BucketInShotFragment newInstance(Buckets buckets) {
        BucketInShotFragment bucketInShotFragment = new BucketInShotFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUCKET, buckets);
        bucketInShotFragment.setArguments(bundle);
        return bucketInShotFragment;
    }


    @Override
    protected void setStrategy() {
        if (getArguments() != null) {
            Buckets mBucket = (Buckets) getArguments().getSerializable(BUCKET);
            mShotListPresenter.setLoadStrategy(new GetShotsFromBucketStrategy(mBucket));
        }
    }


}
