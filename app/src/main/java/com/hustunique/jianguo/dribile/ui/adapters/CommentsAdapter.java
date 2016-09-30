package com.hustunique.jianguo.dribile.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hustunique.jianguo.dribile.models.Comments;
import com.hustunique.jianguo.dribile.presenters.CommentPresenter;
import com.hustunique.jianguo.dribile.ui.activity.ProfileActivity;
import com.hustunique.jianguo.dribile.ui.viewholders.CommentsViewHolder;

/**
 * Created by JianGuo on 4/6/16.
 *
 */
public class CommentsAdapter extends MvpRecyclerListAdapter<Comments, CommentPresenter, CommentsViewHolder> {
    private static final String EXTRA_USER = "EXTRA_USER";
    private Context mContext;

    @LayoutRes
    int layout;


    public CommentsAdapter(Context context, @LayoutRes int layout) {
        mContext = context;
        this.layout = layout;
    }


    public CommentsAdapter() { throw new NullPointerException("You must give an context"); }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentsViewHolder commentsViewHolder = new CommentsViewHolder(LayoutInflater.from(mContext).inflate(layout, parent, false));
        commentsViewHolder.setListener(new CommentsViewHolder.OnCommentClickListener() {
            @Override
            public void onCommentClick(Comments model) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra(EXTRA_USER, model.getUser());
                mContext.startActivity(intent);
            }
        });
        return commentsViewHolder;
    }

    @NonNull
    @Override
    protected CommentPresenter createPresenter(@NonNull Comments model) {
        CommentPresenter commentPresenter = new CommentPresenter();
        commentPresenter.setModel(model);
        return commentPresenter;
    }

    @NonNull
    @Override
    protected Object getModelId(@NonNull Comments model) {
        return model.getId();
    }

}
