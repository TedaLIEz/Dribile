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
 */
public class BucketsAdapter extends MvpRecyclerListAdapter<Buckets, BucketPresenter, BucketsViewHolder> {
    private Context mContext;

    @LayoutRes
    int layout;

    public interface OnItemLongClickListener {
        void onLongClick(Buckets buckets);
    }
    public interface OnItemClickListener {
        void onClick(Buckets buckets);
    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public OnItemLongClickListener onItemLongClickListener;
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public BucketsAdapter() { throw new NullPointerException("You must give an context"); }

    @Override
    public BucketsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BucketsViewHolder bucketsViewHolder = new BucketsViewHolder(LayoutInflater.from(mContext).inflate(layout, parent, false));
        bucketsViewHolder.setListener(new BucketsViewHolder.OnBucketClickListener() {
            @Override
            public void onBucketClick(Buckets model) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(model);
                }
            }
        });
        bucketsViewHolder.setLongClickListener(new BucketsViewHolder.OnBucketLongClickListener() {
            @Override
            public void onLongClick(Buckets model) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onLongClick(model);
                }
            }
        });
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
