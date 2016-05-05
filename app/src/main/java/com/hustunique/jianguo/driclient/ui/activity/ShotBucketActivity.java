package com.hustunique.jianguo.driclient.ui.activity;

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
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.app.PresenterManager;
import com.hustunique.jianguo.driclient.models.Buckets;
import com.hustunique.jianguo.driclient.presenters.ShotBucketPresenter;
import com.hustunique.jianguo.driclient.ui.adapters.BucketsAdapter;
import com.hustunique.jianguo.driclient.ui.widget.AddBucketDialog;
import com.hustunique.jianguo.driclient.ui.widget.DividerItemDecoration;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.hustunique.jianguo.driclient.views.BucketInShotListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activity for adding shot to user's bucket
 */
public class ShotBucketActivity extends BaseActivity implements BucketInShotListView {
    private static final int POS_LIST = 1;
    private static final int POS_LOADING = 0;
    private static final int POS_EMPTY = 2;
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
        mAdapter.setOnItemClickListener(new BucketsAdapter.OnItemClickListener() {
            @Override
            public void onClick(Buckets buckets) {
                mShotBucketPresenter.addToBucket(buckets);
            }
        });
        mAdapter.setOnItemLongClickListener(new BucketsAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(final Buckets buckets) {
                new AlertDialog.Builder(new ContextThemeWrapper(ShotBucketActivity.this, android.R.style.Theme_Holo_Dialog))
                        .setTitle("Delete bucket?")
                        .setMessage("Are you sure you want to delete this bucket?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mShotBucketPresenter.deleteBucket(buckets);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBucketDialog dialog = new AddBucketDialog(ShotBucketActivity.this);
                dialog.show();
                dialog.setOnNegativeButton("No", new AddBucketDialog.OnNegativeButtonListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                dialog.setOnPositiveButton("Yes", new AddBucketDialog.OnPositiveButtonListener() {
                    @Override
                    public void onClick(Dialog dialog, String name, String description) {
                        Log.i("driclient", "get name " + name + "get description " + description);
                        mShotBucketPresenter.createBucket(name, description);
                        dialog.dismiss();
                    }
                });

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
        Snackbar.make(mCoordinatorLayout, "Network Exception!", Snackbar.LENGTH_SHORT).show();
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
                CommonUtils.coloredString(R.string.delete_bucket_success,
                        R.color.colorPrimary,
                        bucket.getName()),
                Snackbar.LENGTH_SHORT).show();
        mAdapter.removeItem(bucket);
    }

    @Override
    public void createBucket(Buckets bucket) {
        Snackbar.make(mViewAnimator,
                CommonUtils.coloredString(R.string.create_bucket_success,
                        R.color.colorPrimary,
                        bucket.getName()),
                Snackbar.LENGTH_SHORT).show();
        mAdapter.addItem(bucket);
    }
}
