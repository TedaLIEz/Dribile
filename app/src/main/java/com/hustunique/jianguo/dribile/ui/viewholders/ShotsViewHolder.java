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

package com.hustunique.jianguo.dribile.ui.viewholders;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.presenters.ShotPresenter;
import com.hustunique.jianguo.dribile.utils.Utils;
import com.hustunique.jianguo.dribile.views.ShotSearchView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by JianGuo on 4/4/16.
 * {@link android.support.v7.widget.RecyclerView.ViewHolder} viewHolder
 * for dribbble shots.
 */
public class ShotsViewHolder extends MvpViewHolder<ShotPresenter> implements ShotSearchView {


    @BindView(R.id.image)
    public ImageView mImage;
    @BindView(R.id.gif_icon)
    public ImageView mGif;
    @BindView(R.id.like_count)
    public TextView mLikeCount;
    @BindView(R.id.comment_count)
    public TextView mCommentCount;
    @BindView(R.id.view_count)
    public TextView mViewCount;

    @BindView(R.id.shots_avatar)
    public ImageView mAvatar;

    @BindView(R.id.shots_title)
    public TextView mTitle;

    @Nullable
    OnShotClickListener mOnShotClickListener;

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
    public void setShotTitle(String title, String keywords) {
        SpannableStringBuilder sb = new SpannableStringBuilder(title);
        ForegroundColorSpan fcs = new ForegroundColorSpan(AppData.getColor(R.color.colorPrimary));
        if (keywords != null) {
            int index = title.toLowerCase().indexOf(keywords.toLowerCase());
            if (index != -1) {
                sb.setSpan(fcs, index, index + keywords.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }
        mTitle.setText(sb);
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
            mImage.setColorFilter(Utils.brightIt(-100));
        } else {
            mGif.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setShotImage(String imageUrl) {
        Picasso.with(itemView.getContext()).load(imageUrl)
                .placeholder(R.drawable.shots_default)
                .into(mImage);
    }

    @Override
    public void setAvatar(Uri avatar_url) {
        Picasso.with(itemView.getContext()).load(avatar_url)
                .placeholder(R.drawable.avatar_default)
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
