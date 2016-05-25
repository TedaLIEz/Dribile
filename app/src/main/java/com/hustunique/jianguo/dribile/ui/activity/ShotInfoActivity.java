package com.hustunique.jianguo.dribile.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.app.PresenterManager;
import com.hustunique.jianguo.dribile.models.Comments;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.models.User;
import com.hustunique.jianguo.dribile.presenters.ShotInfoCommentsPresenter;
import com.hustunique.jianguo.dribile.presenters.ShotInfoPresenter;
import com.hustunique.jianguo.dribile.ui.adapters.CommentsAdapter;
import com.hustunique.jianguo.dribile.ui.widget.DividerItemDecoration;
import com.hustunique.jianguo.dribile.ui.widget.HTMLTextView;
import com.hustunique.jianguo.dribile.utils.CommonUtils;
import com.hustunique.jianguo.dribile.views.ShotInfoCommentView;
import com.hustunique.jianguo.dribile.views.ShotInfoView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

public class ShotInfoActivity extends BaseActivity implements ShotInfoView, ShotInfoCommentView {
    private final static int POS_COMMENTS_SHOW_LOADING = 0;
    private final static int POS_COMMENTS_LOADED = 1;
    private final static int POS_COMMENTS_SHOW_EMPTY = 2;

    private final static int POS_SHOT_TAGS = 0;
    private final static int POS_TAGS_SHOW_EMPTY = 1;
    @Bind(R.id.rv_comments)
    RecyclerView mComments;

    @Bind(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.shot_image)
    ImageView mImageView;


    @Bind(R.id.shot_play)
    ImageView mPlay;
    @Bind(R.id.collapse_toolbar)
    CollapsingToolbarLayout toolbarLayout;

    @Bind(R.id.shots_description)
    HTMLTextView mShotsDescription;

    @Bind(R.id.shots_user)
    TextView mShotsUser;

    @Bind(R.id.shots_time)
    TextView mShotsTime;

    @Bind(R.id.avatar_shots)
    ImageView mAvatar;

    @Bind(R.id.fabtoolbar)
    FABToolbarLayout mFabLayout;

    @Bind(R.id.btn_add_comments)
    ImageButton mAddComments;

    @Bind(R.id.btn_add_like)
    ImageButton mAddLike;

    @Bind(R.id.btn_add_share)
    ImageButton mAddShare;

    @Bind(R.id.btn_add_buckets)
    ImageButton mAddBuckets;

    @Bind(R.id.fabtoolbar_fab)
    FloatingActionButton mFab;
    @Bind(R.id.scroll_content)
    NestedScrollView mScrollView;

    @Bind(R.id.tag_layout)
    FlowLayout mTagLayout;

    //TODO: Find some icons for them!
    @Bind(R.id.shot_bucket_count)
    TextView mBucketCount;
    @Bind(R.id.shot_like_count)
    TextView mLikeCount;
    @Bind(R.id.shot_comments_count)
    TextView mCommentsCount;
    @Bind(R.id.shot_view_count)
    TextView mViewCount;

    @Bind(R.id.comments_animator)
    ViewAnimator mCommentAnimator;
    @Bind(R.id.tags_animator)
    ViewAnimator mTagAnimator;

    @Bind(R.id.comments_footer)
    LinearLayout mFooter;
    @BindDimen(R.dimen.item_divider_size)
    int dividerSize;

    @BindDimen(R.dimen.shot_tag_padding)
    int tagPadding;

    private Shots mShot;
    private LinearLayoutManager linearLayoutManager;
    private CommentsAdapter commentsAdapter;
    private ShotInfoPresenter mShotInfoPresenter;
    private ShotInfoCommentsPresenter mCommentPresenter;

    private
    @ColorInt
    int vibrantColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_info);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (savedInstanceState == null) {
            if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_VIEW)) {
                Uri uri = intent.getData();
                String id = uri.getLastPathSegment().split("-")[0];
                mShotInfoPresenter = new ShotInfoPresenter(id);
                mCommentPresenter = new ShotInfoCommentsPresenter(id);
            } else if (intent.getSerializableExtra("shots") != null) {
                mShot = (Shots) getIntent().getSerializableExtra("shots");
                mShotInfoPresenter = new ShotInfoPresenter(mShot);
                mCommentPresenter = new ShotInfoCommentsPresenter(mShot);
            }
        } else {
            PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }

        initShots();
        initFab();
        initComments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShotInfoPresenter.bindView(this);
        mCommentPresenter.bindView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShotInfoPresenter.unbindView();
        mCommentPresenter.unbindView();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mShotInfoPresenter, outState);
        PresenterManager.getInstance().savePresenter(mCommentPresenter, outState);
    }


    private void initShots() {
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShotInfoPresenter.goToUser();
            }
        });
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Disable the title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShotInfoPresenter.goToDetailView();
            }
        });
    }

    private void initComments() {
        commentsAdapter = new CommentsAdapter(this, R.layout.item_comments);
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY != oldScrollY && mFabLayout.isToolbar()) mFabLayout.hide();
            }
        });

        mFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommentPresenter.goToMoreComments();

            }
        });
        mComments.setAdapter(commentsAdapter);
        mComments.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        linearLayoutManager = new LinearLayoutManager(this);
        // Enable recyclerview scrolled by the wrapped scrollnestedview.
        mComments.setNestedScrollingEnabled(false);
        mComments.setLayoutManager(linearLayoutManager);
    }


    // extract Color from the loaded image
    private void extractColor() {
        mImageView.setDrawingCacheEnabled(true);
        mImageView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mImageView.layout(0, 0,
                mImageView.getMeasuredWidth(), mImageView.getMeasuredHeight());
        mImageView.buildDrawingCache(true);
        Bitmap bitmap = mImageView.getDrawingCache();
        if (bitmap != null) {
            Bitmap bmap = Bitmap.createBitmap(mImageView.getDrawingCache());
            Palette.from(bmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    vibrantColor = palette.getVibrantColor(AppData.getColor(R.color.colorPrimaryDark));
                    //TODO: I hate you Google!
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(vibrantColor);
                    }
                    toolbarLayout.setContentScrimColor(vibrantColor);
                    toolbarLayout.setStatusBarScrimColor(vibrantColor);
                }
            });
        }
        mImageView.setDrawingCacheEnabled(false);

    }


    private void initFab() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFabLayout.show();

            }
        });
        mAddBuckets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShotInfoPresenter.addToBucket();
            }
        });
        mAddLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShotInfoPresenter.onClick();
            }
        });
        mAddShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShotInfoPresenter.sendShared();
                mFabLayout.hide();
            }
        });

        mAddComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShotInfoPresenter.addComments();
                mFabLayout.hide();
            }
        });
    }

    @Override
    public void setShotTitle(final String title) {
        // Correct way showing title only when collapsingToolbarLayout collapses, see http://stackoverflow.com/a/32724422/4380801
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = mAppBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset <= mToolbar.getHeight()) {
                    toolbarLayout.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    toolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void setViewCount(String viewCount) {
        mViewCount.setText(viewCount);
    }

    @Override
    public void setCommentCount(String commentCount) {
        mCommentsCount.setText(commentCount);
    }

    @Override
    public void setLikeCount(String likeCount) {
        mLikeCount.setText(likeCount);
    }

    @Override
    public void setAnimated(boolean animated) {
        if (animated) {
            mImageView.setColorFilter(CommonUtils.brightIt(-100));
            mImageView.setClickable(false);
            mPlay.setVisibility(View.VISIBLE);
            mPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mShotInfoPresenter.goToDetailView();
                }
            });
        }
    }

    @Override
    public void setShotImage(String imageUrl) {
        //TODO: image resize when activity resumes, problem with scaleType?
        Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.shots_default)
                .into(mImageView);
        extractColor();
    }

    @Override
    public void setAvatar(Uri avatar_url) {
        Picasso.with(this).load(avatar_url)
                .placeholder(R.drawable.avatar_default)
                .into(mAvatar);
    }

    @Override
    public void goToDetailView(Shots model) {
        Intent intent = new Intent(ShotInfoActivity.this, ImageDetailActivity.class);
        intent.putExtra(ImageDetailActivity.SHARED_SHOTS, model);
        startActivity(intent);
    }

    @Override
    public void setDefaultAvatar() {

    }

    @Override
    public void goToProfile(User user) {
        Intent intent = new Intent(ShotInfoActivity.this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.USER, user);
        startActivity(intent);
    }

    @Override
    public void addToBucket(Shots model) {
        startActivityWithShot(ShotBucketActivity.class, model);
        mFabLayout.hide();
    }

    @Override
    public void setTags(ArrayList<String> tags) {
        if (tags.size() == 0) {
            mTagAnimator.setDisplayedChild(POS_TAGS_SHOW_EMPTY);
        } else {
            mTagAnimator.setDisplayedChild(POS_SHOT_TAGS);
            for (String tag : tags) {
                TextView textView = new TextView(this);
                textView.setPadding(tagPadding, tagPadding, tagPadding, tagPadding);
                textView.setTextAppearance(this, R.style.TextAppearance_Profile_Title);
                FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(tagPadding, tagPadding, tagPadding, tagPadding);
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                textView.setLayoutParams(layoutParams);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textView.setElevation(AppData.getDimension(R.dimen.cardview_default_elevation));
                }
                textView.setText(tag);
                textView.setBackground(AppData.getDrawable(R.drawable.tag_textview));
                mTagLayout.addView(textView);
            }
        }
    }

    @Override
    public void setDescription(String description) {
        mShotsDescription.setText(description);
    }

    @Override
    public void setUserName(String name) {
        mShotsUser.setText(name);
    }

    @Override
    public void setTime(String time) {
        mShotsTime.setText(time);
    }

    @Override
    public void setBucketCount(String bucketCount) {
        mBucketCount.setText(bucketCount);
    }


    @Override
    public void showEmpty() {
        mCommentAnimator.setDisplayedChild(POS_COMMENTS_SHOW_EMPTY);
    }

    @Override
    public void showLoading() {
        mCommentAnimator.setDisplayedChild(POS_COMMENTS_SHOW_LOADING);
    }

    @Override
    public void onError(Throwable e) {
        Log.wtf("driclient", e);
    }

    @Override
    public void showData(List<Comments> bucketsList) {
        mCommentAnimator.setDisplayedChild(POS_COMMENTS_LOADED);
        commentsAdapter.clearAndAddAll(bucketsList);
    }

    @Override
    public void showLoadingMore() {
        mFooter.setVisibility(View.VISIBLE);
    }

    @Override
    public void goToMoreComments(Shots shots) {
        startActivityWithShot(ShotCommentActivity.class, shots);
    }

    @Override
    public void onLike() {
        mAddLike.setImageDrawable(AppData.getDrawable(R.drawable.ic_favorite_white_36dp));
    }

    @Override
    public void onUnlike() {
        mAddLike.setImageDrawable(AppData.getDrawable(R.drawable.ic_favorite_border_white_36dp));
    }

    @Override
    public void sendSharedIntent(Intent sendIntent) {
        startActivity(sendIntent);
    }

    @Override
    public void addComments(Shots model) {
        startActivityWithShot(ShotCommentActivity.class, model);
    }

}
