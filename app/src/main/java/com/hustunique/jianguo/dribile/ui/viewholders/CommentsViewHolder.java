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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.Comments;
import com.hustunique.jianguo.dribile.presenters.CommentPresenter;
import com.hustunique.jianguo.dribile.ui.widget.HTMLTextView;
import com.hustunique.jianguo.dribile.views.CommentView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JianGuo on 4/6/16.
 * ViewHolder for shot's comments.
 */
public class CommentsViewHolder extends MvpViewHolder<CommentPresenter> implements CommentView {
    @BindView(R.id.comment_avatar)
    public ImageView mAvatar;
    @BindView(R.id.comment_name)
    public TextView mUsername;
    @BindView(R.id.comment_body)
    public HTMLTextView mCommentBody;
    @BindView(R.id.comment_time)
    public TextView mCommentTime;

    @BindView(R.id.comment_like)
    public ImageView mCommentLike;

    @BindView(R.id.comment_like_count)
    public TextView mLikeCount;

    @Nullable
    OnCommentClickListener mOnCommentClickListener;
    public CommentsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCommentClicked();
            }
        });
    }


    public void setListener(@Nullable OnCommentClickListener listener) {
        this.mOnCommentClickListener = listener;
    }



    @Override
    public void setName(String name) {
        mUsername.setText(name);
    }

    @Override
    public void setBody(String body) {
        mCommentBody.setText(body);
    }

    @Override
    public void setTime(String time) {
        mCommentTime.setText(time);
    }

    @Override
    public void setLikeCount(String likeCount) {
        mLikeCount.setText(likeCount);
    }

    @Override
    public void setAvatar(String avatar) {
        Picasso.with(itemView.getContext())
                .load(Uri.parse(avatar))
                .placeholder(AppData.getDrawable(R.drawable.avatar_default))
                .into(mAvatar);
    }

    @Override
    public void goToDetailView(Comments model) {
        if (mOnCommentClickListener != null) {
            mOnCommentClickListener.onCommentClick(model);
        }
    }

    public interface OnCommentClickListener {
        void onCommentClick(Comments model);
    }

}
