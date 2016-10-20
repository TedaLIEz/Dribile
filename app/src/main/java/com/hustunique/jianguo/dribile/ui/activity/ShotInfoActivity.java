package com.hustunique.jianguo.dribile.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
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
import com.hustunique.jianguo.dribile.ui.viewholders.CommentsViewHolder;
import com.hustunique.jianguo.dribile.ui.widget.DividerItemDecoration;
import com.hustunique.jianguo.dribile.ui.widget.HTMLTextView;
import com.hustunique.jianguo.dribile.utils.Utils;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.views.ShotInfoCommentView;
import com.hustunique.jianguo.dribile.views.ShotInfoView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShotInfoActivity extends BaseActivity implements ShotInfoView, ShotInfoCommentView {
    private final static int POS_COMMENTS_SHOW_LOADING = 0;
    private final static int POS_COMMENTS_LOADED = 1;
    private final static int POS_COMMENTS_SHOW_EMPTY = 2;

    private final static int POS_SHOT_TAGS = 0;
    private final static int POS_TAGS_SHOW_EMPTY = 1;
    private static final String TAG = "ShotInfoActivity";
    @BindView(R.id.rv_comments)
    RecyclerView mComments;

    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.shot_image)
    ImageView mImageView;


    @BindView(R.id.shot_play)
    ImageView mPlay;
    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.shots_description)
    HTMLTextView mShotsDescription;

    @BindView(R.id.shots_user)
    TextView mShotsUser;

    @BindView(R.id.shots_time)
    TextView mShotsTime;

    @BindView(R.id.avatar_shots)
    ImageView mAvatar;

    @BindView(R.id.fabtoolbar)
    FABToolbarLayout mFabLayout;

    @BindView(R.id.btn_add_comments)
    ImageButton mAddComments;

    @BindView(R.id.btn_add_like)
    ImageButton mAddLike;

    @BindView(R.id.btn_add_share)
    ImageButton mAddShare;

    @BindView(R.id.btn_add_buckets)
    ImageButton mAddBuckets;

    @BindView(R.id.fabtoolbar_fab)
    FloatingActionButton mFab;
    @BindView(R.id.scroll_content)
    NestedScrollView mScrollView;

    @BindView(R.id.tag_layout)
    FlowLayout mTagLayout;

    //TODO: Find some icons for them!
    @BindView(R.id.shot_bucket_count)
    TextView mBucketCount;
    @BindView(R.id.shot_like_count)
    TextView mLikeCount;
    @BindView(R.id.shot_comments_count)
    TextView mCommentsCount;
    @BindView(R.id.shot_view_count)
    TextView mViewCount;

    @BindView(R.id.comments_animator)
    ViewAnimator mCommentAnimator;
    @BindView(R.id.tags_animator)
    ViewAnimator mTagAnimator;

    @BindView(R.id.comments_footer)
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
            Object shots = intent.getSerializableExtra(Utils.EXTRA_SHOT);
            if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_VIEW)) {
                Uri uri = intent.getData();
                String id = uri.getLastPathSegment().split("-")[0];
                mShotInfoPresenter = new ShotInfoPresenter(id);
                mCommentPresenter = new ShotInfoCommentsPresenter(id);
            } else if (shots != null) {
                mShot = (Shots) shots;
                mShotInfoPresenter = new ShotInfoPresenter(mShot);
                mCommentPresenter = new ShotInfoCommentsPresenter(mShot);
            }
        } else {
            PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }

        initShots();
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


    @OnClick(R.id.avatar_shots)
    void goToUser() {
        mShotInfoPresenter.goToUser();
    }

    @OnClick({R.id.shot_image, R.id.shot_play})
    void goToDetailView() {
        mShotInfoPresenter.goToDetailView();
    }


    @OnClick(R.id.comments_footer)
    void goToMoreComments() {
        mCommentPresenter.goToMoreComments();
    }

    private void initShots() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Disable the title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initComments() {
        commentsAdapter = new CommentsAdapter(this, R.layout.item_comments);
        commentsAdapter.setOnItemClickListener(new CommentsViewHolder.OnCommentClickListener() {
            @Override
            public void onCommentClick(Comments model) {
                Utils.startActivityWithUser(ShotInfoActivity.this, ProfileActivity.class, model.getUser());
            }
        });
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY != oldScrollY && mFabLayout.isToolbar()) mFabLayout.hide();
            }
        });
        mComments.setAdapter(commentsAdapter);
        mComments.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        linearLayoutManager = new LinearLayoutManager(this);
        // Enable recyclerview scrolled by the wrapped scrollnestedview.
        mComments.setFocusable(false);
//        mComments.setNestedScrollingEnabled(false);
        mComments.setLayoutManager(linearLayoutManager);
    }


    // extract Color from the loaded image
    @Deprecated
    private void extractColor() {
        mImageView.setDrawingCacheEnabled(true);
        mImageView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mImageView.layout(0, 0, mImageView.getMeasuredWidth(), mImageView.getMeasuredHeight());
        mImageView.buildDrawingCache(true);
        Bitmap bitmap = mImageView.getDrawingCache();
        if (bitmap != null) {
            Bitmap bmap = Bitmap.createBitmap(mImageView.getDrawingCache());
            Palette.from(bmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    vibrantColor = palette.getVibrantColor(AppData.getColor(R.color.colorPrimaryDark));
                    //I hate you Google!
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


    @OnClick(R.id.fabtoolbar_fab)
    void initFab() {
        mFabLayout.show();
    }


    @OnClick(R.id.btn_add_buckets)
    void addToBucket() {
        mShotInfoPresenter.addToBucket();
    }

    @OnClick(R.id.btn_add_like)
    void addToLike() {
        mShotInfoPresenter.onClick();
    }

    @OnClick(R.id.btn_add_share)
    void sendShared() {
        mShotInfoPresenter.sendShared();
        mFabLayout.hide();
    }

    @OnClick(R.id.btn_add_comments)
    void addComments() {
        mShotInfoPresenter.addComments();
        mFabLayout.hide();
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
            mImageView.setColorFilter(Utils.brightIt(-100));
            mImageView.setClickable(false);
            mPlay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setShotImage(String imageUrl) {
        //TODO: image resize when activity resumes, problem with scaleType?
        Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.shots_default)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mImageView.setImageBitmap(bitmap);
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                vibrantColor = palette.getVibrantColor(AppData.getColor(R.color.colorPrimaryDark));
                                //I hate you Google!
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    getWindow().setStatusBarColor(vibrantColor);
                                }
                                toolbarLayout.setContentScrimColor(vibrantColor);
                                toolbarLayout.setStatusBarScrimColor(vibrantColor);
                            }
                        });
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        mImageView.setImageDrawable(errorDrawable);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        mImageView.setImageDrawable(placeHolderDrawable);
                    }
                });

    }

    @Override
    public void setAvatar(Uri avatar_url) {
        Picasso.with(this).load(avatar_url)
                .placeholder(R.drawable.avatar_default)
                .into(mAvatar);
    }

    @Override
    public void goToDetailView(Shots model) {
        Utils.startActivityWithShot(this, ImageDetailActivity.class, model);
    }

    @Override
    public void setDefaultAvatar() {

    }

    @Override
    public void goToProfile(User user) {
        Utils.startActivityWithUser(this, ProfileActivity.class, user);
    }

    @Override
    public void addToBucket(Shots model) {
        Utils.startActivityWithShot(this, ShotBucketActivity.class, model);
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
        Logger.wtf(TAG, e);
        Toast.makeText(this, AppData.getString(R.string.load_shots_error), Toast.LENGTH_SHORT).show();
//        finish();
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
        Utils.startActivityWithShot(this, ShotCommentActivity.class, shots);
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
        Utils.startActivityWithShot(this, ShotCommentActivity.class, model);
    }

}
