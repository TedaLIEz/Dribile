package com.hustunique.jianguo.dribile.ui.viewholders;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.presenters.ShotPresenter;
import com.hustunique.jianguo.dribile.utils.CommonUtils;
import com.hustunique.jianguo.dribile.views.ShotView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by JianGuo on 4/4/16.
 * {@link android.support.v7.widget.RecyclerView.ViewHolder} viewHolder
 * for dribbble shots.
 */
public class ShotsViewHolder extends MvpViewHolder<ShotPresenter> implements ShotView {


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

    @Nullable OnShotClickListener mOnShotClickListener;
    public ShotsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onShotClicked();
            }
        });
    }

    @Override
    public void setShotTitle(String title) {
        mTitle.setText(title);
    }

    public void setListener(@Nullable OnShotClickListener listener) {
        this.mOnShotClickListener = listener;
    }

    @Override
    public void setViewCount(String viewCount) {
        mViewCount.setText(viewCount);
    }

    @Override
    public void setCommentCount(String commentCount) {
        mCommentCount.setText(commentCount);
    }

    @Override
    public void setLikeCount(String likeCount) {
        mLikeCount.setText(likeCount);
    }

    @Override
    public void setAnimated(boolean animated) {
        if (animated) {
            mGif.setVisibility(View.VISIBLE);
            mImage.setColorFilter(CommonUtils.brightIt(-100));
        } else {
            mGif.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setShotImage(Uri imageUrl) {
        Picasso.with(itemView.getContext()).load(imageUrl)
                .placeholder(AppData.getDrawable(R.drawable.shots_default))
                .into(mImage);
    }

    @Override
    public void setAvatar(Uri avatar_url) {
        Picasso.with(itemView.getContext()).load(avatar_url)
                .placeholder(AppData.getDrawable(R.drawable.shots_default))
                .into(mAvatar);
    }

    @Override
    public void goToDetailView(Shots model) {
        if (mOnShotClickListener != null) {
            mOnShotClickListener.onShotClick(model);
        }
    }

    @Override
    public void setDefaultAvatar() {
        mAvatar.setVisibility(View.GONE);
    }

    public interface OnShotClickListener {
        void onShotClick(Shots model);
    }


}
