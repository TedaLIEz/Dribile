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

package com.hustunique.jianguo.dribile.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.presenters.BucketPresenter;
import com.hustunique.jianguo.dribile.ui.viewholders.BucketsViewHolder;

/**
 * Created by JianGuo on 4/19/16.
 * Adapter for buckets list
 */
public class BucketsAdapter extends MvpRecyclerListAdapter<Buckets, BucketPresenter, BucketsViewHolder> {
    private Context mContext;

    @LayoutRes
    int layout;
    private BucketsViewHolder.OnBucketClickListener onItemClickListener;
    private BucketsViewHolder.OnBucketLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(BucketsViewHolder.OnBucketClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void setOnItemLongClickListener(BucketsViewHolder.OnBucketLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public BucketsAdapter() { throw new NullPointerException("You must give an context"); }

    @Override
    public BucketsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BucketsViewHolder bucketsViewHolder = new BucketsViewHolder(LayoutInflater.from(mContext).inflate(layout, parent, false));
        bucketsViewHolder.setListener(onItemClickListener);
        bucketsViewHolder.setLongClickListener(onItemLongClickListener);
        return bucketsViewHolder;
    }

    @NonNull
    @Override
    protected BucketPresenter createPresenter(@NonNull Buckets model) {
        BucketPresenter bucketPresenter = new BucketPresenter();
        bucketPresenter.setModel(model);
        return bucketPresenter;
    }

    @NonNull
    @Override
    protected Object getModelId(@NonNull Buckets model) {
        return model.getId();
    }

    public BucketsAdapter(Context ctx, @LayoutRes int layout) {
        mContext = ctx;
        this.layout = layout;
    }
}
