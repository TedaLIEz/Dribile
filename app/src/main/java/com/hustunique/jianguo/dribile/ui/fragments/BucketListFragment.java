/*
 * Copyright (c) 2016.  TedaLIEz <aliezted@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hustunique.jianguo.dribile.ui.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
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

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.app.PresenterManager;
import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.presenters.BucketListPresenter;
import com.hustunique.jianguo.dribile.ui.activity.BucketDetailActivity;
import com.hustunique.jianguo.dribile.ui.adapters.BucketsAdapter;
import com.hustunique.jianguo.dribile.ui.viewholders.BucketsViewHolder;
import com.hustunique.jianguo.dribile.ui.widget.AddBucketDialog;
import com.hustunique.jianguo.dribile.ui.widget.DividerItemDecoration;
import com.hustunique.jianguo.dribile.utils.Utils;
import com.hustunique.jianguo.dribile.views.BucketListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Use the {@link BucketListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BucketListFragment extends BaseFragment implements BucketListView, IShotFragment {
    private static final int POS_LIST = 1;
    private static final int POS_EMPTY = 2;
    private static final int POS_LOADING = 0;
    private static final String TAG = "BucketListFragment";
    @BindView(R.id.rv_buckets)
    RecyclerView mBuckets;


    @BindView(R.id.animator)
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
        dialog.setOnNegativeButton(AppData.getString(R.string.no), new AddBucketDialog.OnNegativeButtonListener() {
            @Override
            public void onClick(Dialog dialog) {
                dialog.dismiss();
            }
        });
        dialog.setOnPositiveButton(AppData.getString(R.string.yes), new AddBucketDialog.OnPositiveButtonListener() {
            @Override
            public void onClick(Dialog dialog, String name, String description) {
                mBucketListPresenter.createBucket(name, description);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void search(String query) {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
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
        mAdapter.setOnItemClickListener(new BucketsViewHolder.OnBucketClickListener() {
            @Override
            public void onBucketClick(Buckets model) {
                if (model.getShots_count().equals("0")) {
                    return;
                }
                Utils.startActivityWithBucket(getActivity(), BucketDetailActivity.class, model);
            }
        });
        mAdapter.setOnItemLongClickListener(new BucketsViewHolder.OnBucketLongClickListener() {
            @Override
            public void onLongClick(final Buckets model) {
                new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Dialog))
                        .setTitle(AppData.getString(R.string.delete_bucket_title))
                        .setMessage(AppData.getString(R.string.delete_bucket_content))
                        .setPositiveButton(AppData.getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mBucketListPresenter.deleteBucket(model);
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
        Log.wtf(TAG, e);
        Snackbar.make(getActivity().findViewById(android.R.id.content), AppData.getString(R.string.create_bucket_fail), Snackbar.LENGTH_SHORT).show();
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
        Snackbar.make(getActivity().findViewById(R.id.fab),
                Utils.coloredString(R.string.delete_bucket_success,
                        R.color.colorPrimary,
                        bucket.getName()+""),
                Snackbar.LENGTH_SHORT).show();
        mAdapter.removeItem(bucket);
    }

    @Override
    public void createBucket(Buckets bucket) {
        Snackbar.make(getActivity().findViewById(R.id.fab),
                Utils.coloredString(R.string.create_bucket_success,
                        R.color.colorPrimary,
                        bucket.getName()+""),
                Snackbar.LENGTH_SHORT).show();
        mAdapter.addItem(bucket);
    }
}
