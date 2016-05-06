package com.hustunique.jianguo.driclient.ui.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.PresenterManager;
import com.hustunique.jianguo.driclient.models.Buckets;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.presenters.BucketListPresenter;
import com.hustunique.jianguo.driclient.presenters.ShotListPresenter;
import com.hustunique.jianguo.driclient.presenters.strategy.GetShotByIdStrategy;
import com.hustunique.jianguo.driclient.presenters.strategy.GetShotsFromBucketStrategy;
import com.hustunique.jianguo.driclient.service.DribbbleBucketsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.driclient.ui.adapters.BucketsAdapter;
import com.hustunique.jianguo.driclient.ui.adapters.ShotsAdapter;
import com.hustunique.jianguo.driclient.ui.widget.PaddingItemDecoration;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.hustunique.jianguo.driclient.views.ILoadListView;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import rx.Observable;

/**
 * Created by JianGuo on 4/21/16.
 * Fragment for listing shots in the bucket
 */
public class BucketInShotFragment extends BaseShotListFragment {
    public static final String BUCKET = "bucket";

    private Buckets mBucket;

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
            mBucket = (Buckets) getArguments().getSerializable(BUCKET);
            mShotListPresenter.setLoadStrategy(new GetShotsFromBucketStrategy(mBucket));
        }
    }


}
