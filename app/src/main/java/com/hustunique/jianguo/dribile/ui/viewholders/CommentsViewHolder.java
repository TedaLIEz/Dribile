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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JianGuo on 4/6/16.
 * ViewHolder for shot's comments.
 */
public class CommentsViewHolder extends MvpViewHolder<CommentPresenter> implements CommentView {
    @Bind(R.id.comment_avatar)
    public ImageView mAvatar;
    @Bind(R.id.comment_name)
    public TextView mUsername;
    @Bind(R.id.comment_body)
    public HTMLTextView mCommentBody;
    @Bind(R.id.comment_time)
    public TextView mCommentTime;

    @Bind(R.id.comment_like)
    public ImageView mCommentLike;

    @Bind(R.id.comment_like_count)
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
