package com.hustunique.jianguo.driclient.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.models.Buckets;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JianGuo on 4/19/16.
 */
public class BucketsViewHolder extends BaseViewHolder<Buckets> {

    @Bind(R.id.bucket_count)
    public TextView mCount;
    @Bind(R.id.bucket_name)
    public TextView mName;

    public BucketsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
