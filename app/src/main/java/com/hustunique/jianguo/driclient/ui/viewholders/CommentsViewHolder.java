package com.hustunique.jianguo.driclient.ui.viewholders;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.models.Comments;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.presenters.CommentPresenter;
import com.hustunique.jianguo.driclient.ui.widget.HTMLTextView;
import com.hustunique.jianguo.driclient.views.CommentView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Comment;

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

    }

    public interface OnCommentClickListener {
        void onCommentClick(Comments model);
    }

}
