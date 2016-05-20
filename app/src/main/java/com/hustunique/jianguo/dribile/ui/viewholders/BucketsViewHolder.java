package com.hustunique.jianguo.dribile.ui.viewholders;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.presenters.BucketPresenter;
import com.hustunique.jianguo.dribile.views.BucketView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JianGuo on 4/19/16.
 * ViewHolder for bucket item
 */
public class BucketsViewHolder extends MvpViewHolder<BucketPresenter> implements BucketView {

    @Bind(R.id.bucket_count)
    public TextView mCount;
    @Bind(R.id.bucket_name)
    public TextView mName;

    @Nullable
    OnBucketClickListener mOnShotClickListener;

    @Nullable
    OnBucketLongClickListener mOnLongClickListener;

    public interface OnBucketClickListener {
        void onBucketClick(Buckets model);
    }

    public interface OnBucketLongClickListener {
        void onLongClick(Buckets model);
    }

    public void setListener(OnBucketClickListener listener) {
        this.mOnShotClickListener = listener;
    }

    public void setLongClickListener(OnBucketLongClickListener mOnLongClickListener) {
        this.mOnLongClickListener = mOnLongClickListener;
    }

    public BucketsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBucketClicked();
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return presenter.onBucketLongClick();
            }
        });
    }


    @Override
    public void setCount(String count) {
        mCount.setText(count);
    }

    @Override
    public void setName(String name) {
        mName.setText(name);
    }

    @Override
    public void goToDetailView(Buckets bucket) {
        if (mOnShotClickListener != null) {
            mOnShotClickListener.onBucketClick(bucket);
        }
    }




    @Override
    public boolean onLongClick(Buckets model) {
        if (mOnLongClickListener != null) {
            mOnLongClickListener.onLongClick(model);
            return true;
        }
        return false;
    }
}
