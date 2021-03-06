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

package com.hustunique.jianguo.dribile.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.PresenterManager;
import com.hustunique.jianguo.dribile.models.Comments;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.presenters.CommentListPresenter;
import com.hustunique.jianguo.dribile.ui.adapters.CommentsAdapter;
import com.hustunique.jianguo.dribile.ui.viewholders.CommentsViewHolder;
import com.hustunique.jianguo.dribile.ui.widget.DividerItemDecoration;
import com.hustunique.jianguo.dribile.utils.Utils;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.views.CommentListView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

public class ShotCommentActivity extends BaseActivity implements CommentListView {
    private static final int POS_LOADING = 0;
    private static final int POS_SHOW_DATA = 1;
    private static final int POS_EMPTY = 2;
    private static final String TAG = "ShotCommentActivity";

    @BindView(R.id.comments_count)
    TextView mCommentsTitle;
    @BindView(R.id.comments_detail)
    TextView mCommentsSubtitle;
    @BindView(R.id.comments_list)
    RecyclerView mComments;
    @BindView(R.id.avatar_comments)
    CircleImageView mAvatar;
    @BindView(R.id.btn_send_comments)
    FloatingActionButton mSend;
    @BindView(R.id.et_add_comment)
    EditText mEditText;
    @BindView(R.id.animator)
    ViewAnimator mAnimator;

    private CommentsAdapter mAdapter;
    private CommentListPresenter mCommentListPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_comment);

        ButterKnife.bind(this);
        Shots mShot = (Shots) getIntent().getSerializableExtra(Utils.EXTRA_SHOT);
        if (mShot == null || TextUtils.isEmpty(mShot.getComments_url())) {
            throw new NullPointerException("shot in ShotCommentActivity mustn't be null");
        }
        if (savedInstanceState == null) {
            mCommentListPresenter = new CommentListPresenter(mShot);
        } else {
            PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
        initView();

    }



    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommentListPresenter.sendComments(mEditText.getText().toString());
                Utils.hideSoftInputFromWindow(ShotCommentActivity.this);
            }
        });
        initCommentsRecyclerView();
    }

    //TODO: incorrect position of recyclerView
    private void initCommentsRecyclerView() {
        mAdapter = new CommentsAdapter(this, R.layout.item_comments);
        mAdapter.setOnItemClickListener(new CommentsViewHolder.OnCommentClickListener() {
            @Override
            public void onCommentClick(Comments model) {
                Utils.startActivityWithUser(ShotCommentActivity.this, ProfileActivity.class, model.getUser());
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mComments.setAdapter(new SlideInBottomAnimationAdapter(mAdapter));
        mComments.setLayoutManager(linearLayoutManager);
        mComments.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
    }


    @Override
    public void onAddCommentSuccess(Comments comment) {
        mEditText.setText("");
        mAdapter.addItem(comment);
        mComments.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public void onAddCommentError(Throwable e) {
        Logger.wtf(TAG, e);
        mEditText.setText("");
    }

    @Override
    public void setTitle(String title) {
        mCommentsTitle.setText(title);
    }

    @Override
    public void setSubTitle(String subTitle) {
        mCommentsSubtitle.setText(subTitle);
    }

    @Override
    public void setAvatar(Uri avatar_url) {
        Picasso.with(this).load(avatar_url)
                .into(mAvatar);
    }

    @Override
    public void showEmpty() {
        mAnimator.setDisplayedChild(POS_EMPTY);
    }

    @Override
    public void showLoading() {
        mAnimator.setDisplayedChild(POS_LOADING);
    }

    @Override
    public void onError(Throwable e) {
        Logger.wtf(TAG, e);
    }

    @Override
    public void showData(List<Comments> commentsList) {
        mAnimator.setDisplayedChild(POS_SHOW_DATA);
        mAdapter.clearAndAddAll(commentsList);
    }

    @Override
    public void showLoadingMore() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        mCommentListPresenter.bindView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCommentListPresenter.unbindView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mCommentListPresenter, outState);
    }
}
