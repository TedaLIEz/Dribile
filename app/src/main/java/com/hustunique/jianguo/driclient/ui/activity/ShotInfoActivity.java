package com.hustunique.jianguo.driclient.ui.activity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.app.UserManager;
import com.hustunique.jianguo.driclient.bean.Comments;
import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleShotsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.driclient.ui.adapters.CommentsAdapter;
import com.hustunique.jianguo.driclient.ui.widget.DividerItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShotInfoActivity extends BaseActivity {
    @Bind(R.id.rootLayout)
    CoordinatorLayout rootLayout;
    @Bind(R.id.rv_comments)
    RecyclerView mComments;

    @Bind(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.shot_image)
    ImageView mImageView;

    @Bind(R.id.fab_avatar)
    FloatingActionButton mFab;

    @Bind(R.id.collapse_toolbar)
    CollapsingToolbarLayout toolbarLayout;

    @Bind(R.id.comment_loading)
    ProgressBar mProgress;

    @BindDimen(R.dimen.item_divider_size)
    int dividerSize;

    private Shots mShot;

    private LinearLayoutManager linearLayoutManager;
    private CommentsAdapter commentsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_info);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            mShot = (Shots) getIntent().getSerializableExtra("shots");
        } else {
            throw new NullPointerException("No shots was specified in the MainActivity " + getIntent().toString());
        }
        initToolbar();
        initFab();
        initRecyclerView();
    }

    // TODO: Set layout of the designer's information
    private void initRecyclerView() {
        commentsAdapter = new CommentsAdapter(this, R.layout.item_comments);
        mComments.setAdapter(commentsAdapter);
        mComments.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        linearLayoutManager = new LinearLayoutManager(this);
        mComments.setHasFixedSize(true);
        mComments.setLayoutManager(linearLayoutManager);
        DribbbleShotsService commentsService = ApiServiceFactory.createService(DribbbleShotsService.class, UserManager.getCurrentToken());
        commentsService.getComment(mShot.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Comments>>() {
                    @Override
                    public void onCompleted() {
                        mProgress.setVisibility(View.GONE);
                        mComments.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.wtf("driclient", e);
                    }

                    @Override
                    public void onNext(List<Comments> commentses) {
                        commentsAdapter.clearData();
                        commentsAdapter.setDataAfter(commentses);
                    }
                });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Disable the title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Picasso.with(this).load(Uri.parse(mShot.getImages().getNormal())).placeholder(AppData.getDrawable(R.drawable.shot_info_image_default)).into(mImageView);
        // Correct way showing title only when collapsingToolbarLayout collapses, see http://stackoverflow.com/a/32724422/4380801
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = mAppBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbarLayout.setTitle(mShot.getTitle());
                    isShow = true;
                } else if (isShow) {
                    toolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initFab() {
        // TODO: Not very good to load the designer's avatar here!
//        Picasso.with(this).load(Uri.parse(mShot.getUser().getAvatar_url())).placeholder(AppData.getDrawable(R.drawable.avatar_default)).into(mFab);

        //TODO: Add comment
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    @Override
    public void onBackPressed() {

        //TODO: When scroll down already, first back to header.
        if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() != 0) {
            expandToolbar();
            mComments.smoothScrollToPosition(0);
        } else {
            super.onBackPressed();
        }

    }



    public void expandToolbar(){
        // Use this in v23, some buggy in setTitle
        mAppBarLayout.setExpanded(true, false);
//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
//        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
//        if(behavior!=null) {
//            behavior.setTopAndBottomOffset(0);
////            behavior.onNestedFling(rootLayout, mAppBarLayout, null, 0, -mAppBarLayout.getHeight(), false);
//        }
    }


}
