package com.hustunique.jianguo.driclient.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.models.Buckets;
import com.hustunique.jianguo.driclient.ui.viewholders.BaseViewHolder;
import com.hustunique.jianguo.driclient.ui.viewholders.BucketsViewHolder;

/**
 * Created by JianGuo on 4/19/16.
 */
public class BucketsAdapter extends BaseDriListAdapter<Buckets> {
    private Context mContext;

    @LayoutRes
    int layout;


    public interface OnItemClickListener {
        void onClick(View v, Buckets buckets);
    }


    public interface OnItemLongClickListener {
        void onLongClick(View v, Buckets buckets);
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

    public BucketsAdapter(Context ctx, @LayoutRes int layout) {
        mContext = ctx;
        this.layout = layout;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layout, parent, false);
        return new BucketsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        BucketsViewHolder bucketsViewHolder = (BucketsViewHolder) holder;
        final Buckets buckets = getItem(position);
        bucketsViewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, buckets);
                }
            }
        });
        bucketsViewHolder.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onLongClick(v, buckets);
                    return true;
                }
                return false;
            }
        });
        bucketsViewHolder.setData(buckets);
        bucketsViewHolder.mCount.setText(String.format(AppData.getString(R.string.buckets_count), buckets.getShots_count()));
        bucketsViewHolder.mName.setText(buckets.getName());
        super.onBindViewHolder(holder, position);
    }
}
