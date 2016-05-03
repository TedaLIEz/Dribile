package com.hustunique.jianguo.driclient.ui.viewholders;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.views.ShotView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by JianGuo on 4/4/16.
 * {@link android.support.v7.widget.RecyclerView.ViewHolder} viewHolder
 * for dribbble shots.
 */
public class ShotsViewHolder extends BaseViewHolder<Shots> implements ShotView {


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


    @Override
    public void setShotTitle(String title) {

    }

    @Override
    public void setViewCount(String viewCount) {

    }

    @Override
    public void setCommentCount(String commentCount) {

    }

    @Override
    public void setLikeCount(String likeCount) {

    }

    @Override
    public void setAnimated(String animated) {

    }

    @Override
    public void loadImage(Drawable drawable) {
        mImage.setImageDrawable(drawable);
    }
}
