package com.hustunique.jianguo.driclient.ui.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.bean.Shots;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by JianGuo on 4/4/16.
 * {@link android.support.v7.widget.RecyclerView.ViewHolder} viewHolder
 * for dribbble shots.
 */
public class ShotsViewHolder extends BaseViewHolder<Shots> {

    //TODO: Change the layout of shots snapshots.

    @Bind(R.id.image)
    public ImageView mImage;
//    @Bind(R.id.avatar)
//    public ImageView mAvator;
    @Bind(R.id.like_count)
    public TextView mLikeCount;
    @Bind(R.id.comment_count)
    public TextView mCommentCount;
    @Bind(R.id.view_count)
    public TextView mViewCount;

    public ShotsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
