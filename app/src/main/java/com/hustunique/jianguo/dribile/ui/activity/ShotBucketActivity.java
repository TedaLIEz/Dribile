package com.hustunique.jianguo.dribile.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.app.PresenterManager;
import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.presenters.ShotBucketPresenter;
import com.hustunique.jianguo.dribile.ui.adapters.BucketsAdapter;
import com.hustunique.jianguo.dribile.ui.viewholders.BucketsViewHolder;
import com.hustunique.jianguo.dribile.ui.widget.AddBucketDialog;
import com.hustunique.jianguo.dribile.ui.widget.DividerItemDecoration;
import com.hustunique.jianguo.dribile.utils.Utils;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.views.BucketInShotListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity for adding shot to user's bucket
 */
public class ShotBucketActivity extends BaseActivity implements BucketInShotListView {
    private static final int POS_LIST = 1;
    private static final int POS_LOADING = 0;
    private static final int POS_EMPTY = 2;
    private static final String TAG = "ShotBucketActivity";
    @Bind(R.id.rv_buckets)
    RecyclerView mBuckets;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.shots_detail)
    TextView mTextView;
    @Bind(R.id.animator)
    ViewAnimator mViewAnimator;
    @Bind(R.id.rootView)
    CoordinatorLayout mCoordinatorLayout;
    private BucketsAdapter mAdapter;

    private ShotBucketPresenter mShotBucketPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_bucket);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (savedInstanceState == null) {
            mShotBucketPresenter = new ShotBucketPresenter(getIntent());
        } else {
            mShotBucketPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
        initView();
    }

    private void initView() {
        mAdapter = new BucketsAdapter(this, R.layout.item_buckets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBuckets.setLayoutManager(linearLayoutManager);
        mBuckets.setAdapter(mAdapter);
        mBuckets.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        mAdapter.setOnItemClickListener(new BucketsViewHolder.OnBucketClickListener() {
            @Override
            public void onBucketClick(Buckets model) {
                mShotBucketPresenter.addToBucket(model);
            }
        });
        mAdapter.setOnItemLongClickListener(new BucketsViewHolder.OnBucketLongClickListener() {
            @Override
            public void onLongClick(final Buckets model) {
                new AlertDialog.Builder(new ContextThemeWrapper(ShotBucketActivity.this, android.R.style.Theme_Holo_Dialog))
                        .setTitle(AppData.getString(R.string.delete_bucket_title))
                        .setMessage(AppData.getString(R.string.delete_bucket_content))
                        .setPositiveButton(AppData.getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mShotBucketPresenter.deleteBucket(model);
                            }
                        })
                        .setNegativeButton(AppData.getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    @OnClick(R.id.fab)
    void initFab() {
        AddBucketDialog dialog = new AddBucketDialog(ShotBucketActivity.this);
        dialog.show();
        dialog.setOnNegativeButton(AppData.getString(R.string.no), new AddBucketDialog.OnNegativeButtonListener() {
            @Override
            public void onClick(Dialog dialog) {
                dialog.dismiss();
            }
        });
        dialog.setOnPositiveButton(AppData.getString(R.string.yes), new AddBucketDialog.OnPositiveButtonListener() {
            @Override
            public void onClick(Dialog dialog, String name, String description) {
                Logger.i(TAG, "get name " + name + "get description " + description);
                mShotBucketPresenter.createBucket(name, description);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void addToBucket(final Buckets bucket) {
        showMessage(AppData.getString(R.string.add_to_bucket_success, bucket.getName()));
        finish();
    }

    @Override
    public void setTitle(String title) {
        mTextView.setText(title);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mShotBucketPresenter.bindView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShotBucketPresenter.unbindView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mShotBucketPresenter, outState);
    }

    @Override
    public void showEmpty() {

        mViewAnimator.setDisplayedChild(POS_EMPTY);
    }

    @Override
    public void showLoading() {
        mViewAnimator.setDisplayedChild(POS_LOADING);
    }

    @Override
    public void onError(Throwable e) {
        Snackbar.make(mCoordinatorLayout, AppData.getString(R.string.network_error), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showData(List<Buckets> bucketsList) {
        mAdapter.addAll(bucketsList);
        mViewAnimator.setDisplayedChild(POS_LIST);
    }

    @Override
    public void showLoadingMore() {

    }

    @Override
    public void removeBucket(Buckets bucket) {
        Snackbar.make(mViewAnimator,
                Utils.coloredString(R.string.delete_bucket_success,
                        R.color.colorPrimary,
                        bucket.getName()),
                Snackbar.LENGTH_SHORT).show();
        mAdapter.removeItem(bucket);
    }

    @Override
    public void createBucket(Buckets bucket) {
        Snackbar.make(mViewAnimator,
                Utils.coloredString(R.string.create_bucket_success,
                        R.color.colorPrimary,
                        bucket.getName()),
                Snackbar.LENGTH_SHORT).show();
        mAdapter.addItem(bucket);
    }
}
