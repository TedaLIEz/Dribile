package com.hustunique.jianguo.driclient.ui.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.PresenterManager;
import com.hustunique.jianguo.driclient.models.Buckets;
import com.hustunique.jianguo.driclient.presenters.BucketListPresenter;
import com.hustunique.jianguo.driclient.ui.activity.BucketDetailActivity;
import com.hustunique.jianguo.driclient.ui.adapters.BucketsAdapter;
import com.hustunique.jianguo.driclient.ui.widget.AddBucketDialog;
import com.hustunique.jianguo.driclient.ui.widget.DividerItemDecoration;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.hustunique.jianguo.driclient.views.BucketListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Use the {@link BucketListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BucketListFragment extends BaseFragment implements BucketListView, IFabClickFragment {
    private static final int POS_LIST = 1;
    private static final int POS_EMPTY = 2;
    private static final int POS_LOADING = 0;
    @Bind(R.id.rv_buckets)
    RecyclerView mBuckets;


    @Bind(R.id.animator)
    ViewAnimator mViewAnimator;

    private BucketsAdapter mAdapter;
    private BucketListPresenter mBucketListPresenter;



    public BucketListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onFabClick() {
        AddBucketDialog dialog = new AddBucketDialog(getActivity());
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
                mBucketListPresenter.createBucket(name, description);
                dialog.dismiss();
            }
        });
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BucketListFragment.
     */
    public static BucketListFragment newInstance() {
        BucketListFragment fragment = new BucketListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mBucketListPresenter = new BucketListPresenter();
        } else {
            mBucketListPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mBucketListPresenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBucketListPresenter.unbindView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mBucketListPresenter, outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bucket, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mAdapter = new BucketsAdapter(getActivity(), R.layout.item_buckets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mBuckets.setLayoutManager(linearLayoutManager);
        mBuckets.setAdapter(mAdapter);
        mBuckets.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider));
        mAdapter.setOnItemClickListener(new BucketsAdapter.OnItemClickListener() {
            @Override
            public void onClick(Buckets buckets) {
                showShots(buckets);
            }

            private void showShots(Buckets buckets) {
                Intent intent = new Intent(getActivity(), BucketDetailActivity.class);
                intent.putExtra(BucketDetailActivity.BUCKET, buckets);
                startActivity(intent);
            }
        });
        mAdapter.setOnItemLongClickListener(new BucketsAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(final Buckets buckets) {
                new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Dialog))
                        .setTitle("Delete bucket?")
                        .setMessage("Are you sure you want to delete this bucket?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mBucketListPresenter.deleteBucket(buckets);
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
        Log.wtf("driclient", e);
        Snackbar.make(mViewAnimator, "Failed to create bucket", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showData(List<Buckets> bucketsList) {
        mAdapter.clearAndAddAll(bucketsList);
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
                        bucket.getName()+""),
                Snackbar.LENGTH_SHORT).show();
        mAdapter.removeItem(bucket);
    }

    @Override
    public void createBucket(Buckets bucket) {
        Snackbar.make(mViewAnimator,
                CommonUtils.coloredString(R.string.create_bucket_success,
                        R.color.colorPrimary,
                        bucket.getName()+""),
                Snackbar.LENGTH_SHORT).show();
        mAdapter.addItem(bucket);
    }
}
