package com.hustunique.jianguo.driclient.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.app.PresenterManager;
import com.hustunique.jianguo.driclient.app.UserManager;
import com.hustunique.jianguo.driclient.models.Comments;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.presenters.CommentListPresenter;
import com.hustunique.jianguo.driclient.service.DribbbleShotsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.driclient.ui.adapters.CommentsAdapter;
import com.hustunique.jianguo.driclient.ui.widget.DividerItemDecoration;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.hustunique.jianguo.driclient.views.CommentListView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShotCommentActivity extends BaseActivity implements CommentListView {
    private static final int POS_LOADING = 0;
    private static final int POS_SHOW_DATA = 1;
    private static final int POS_EMPTY = 2;

    @Bind(R.id.comments_count)
    TextView mCommentsTitle;
    @Bind(R.id.comments_detail)
    TextView mCommentsSubtitle;
    @Bind(R.id.comments_list)
    RecyclerView mComments;
    @Bind(R.id.avatar_comments)
    CircleImageView mAvatar;
    @Bind(R.id.btn_send_comments)
    FloatingActionButton mSend;
    @Bind(R.id.et_add_comment)
    EditText mEditText;
    @Bind(R.id.animator)
    ViewAnimator mAnimator;

    private CommentsAdapter mAdapter;
    private CommentListPresenter mCommentListPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_comment);

        ButterKnife.bind(this);
        Shots mShot = (Shots) getIntent().getSerializableExtra(SHOT);
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
                CommonUtils.hideSoftInputFromWindow(ShotCommentActivity.this);
            }
        });
        initCommentsRecyclerView();
    }

    //TODO: incorrect position of recyclerView
    private void initCommentsRecyclerView() {
        mAdapter = new CommentsAdapter(this, R.layout.item_comments);
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
        Log.wtf("driclient", e);
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
        Log.wtf("driclient", e);
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
