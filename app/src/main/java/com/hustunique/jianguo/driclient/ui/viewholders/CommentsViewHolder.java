package com.hustunique.jianguo.driclient.ui.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.bean.Comments;
import com.hustunique.jianguo.driclient.ui.widget.HTMLTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JianGuo on 4/6/16.
 * ViewHolder for shot's comments.
 */
public class CommentsViewHolder extends BaseViewHolder<Comments> {
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

    public CommentsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
