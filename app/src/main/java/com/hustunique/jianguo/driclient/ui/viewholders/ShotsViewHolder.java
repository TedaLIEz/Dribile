package com.hustunique.jianguo.driclient.ui.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.models.Shots;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by JianGuo on 4/4/16.
 * {@link android.support.v7.widget.RecyclerView.ViewHolder} viewHolder
 * for dribbble shots.
 */
public class ShotsViewHolder extends BaseViewHolder<Shots> {


    @Bind(R.id.image)
    public ImageView mImage;
    @Bind(R.id.gif_icon)
    public ImageView mGif;
    @Bind(R.id.like_count)
    public TextView mLikeCount;
    @Bind(R.id.comment_count)
    public TextView mCommentCount;
    @Bind(R.id.view_count)
    public TextView mViewCount;

    @Bind(R.id.shots_avatar)
    public ImageView mAvatar;

    @Bind(R.id.shots_title)
    public TextView mTitle;
    public ShotsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
