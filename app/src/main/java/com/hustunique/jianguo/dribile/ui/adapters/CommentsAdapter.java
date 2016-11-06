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

package com.hustunique.jianguo.dribile.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hustunique.jianguo.dribile.models.Comments;
import com.hustunique.jianguo.dribile.presenters.CommentPresenter;
import com.hustunique.jianguo.dribile.ui.activity.ProfileActivity;
import com.hustunique.jianguo.dribile.ui.viewholders.CommentsViewHolder;

/**
 * Created by JianGuo on 4/6/16.
 * Adapter for comments list
 */
public class CommentsAdapter extends MvpRecyclerListAdapter<Comments, CommentPresenter, CommentsViewHolder> {
    private Context mContext;
    CommentsViewHolder.OnCommentClickListener listener;
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
        commentsViewHolder.setListener(listener);
        return commentsViewHolder;
    }

    public void setOnItemClickListener(@Nullable CommentsViewHolder.OnCommentClickListener listener) {
        this.listener = listener;
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
