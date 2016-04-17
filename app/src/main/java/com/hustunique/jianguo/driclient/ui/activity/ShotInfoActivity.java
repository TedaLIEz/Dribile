package com.hustunique.jianguo.driclient.ui.activity;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.app.UserManager;
import com.hustunique.jianguo.driclient.bean.Comments;
import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleShotsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.driclient.ui.adapters.CommentsAdapter;
import com.hustunique.jianguo.driclient.ui.widget.DividerItemDecoration;
import com.hustunique.jianguo.driclient.ui.widget.HTMLTextView;
import com.hustunique.jianguo.driclient.ui.widget.ShotLikeClickListener;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.squareup.picasso.Picasso;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShotInfoActivity extends BaseActivity {
    private static final int COMMENTS_PER_PAGE = 5;
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

    @Bind(R.id.comment_loading)
    ProgressBar mProgress;

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


    @Bind(R.id.comments_footer)
    LinearLayout mFooter;
    @BindDimen(R.dimen.item_divider_size)
    int dividerSize;

    @BindDimen(R.dimen.shot_tag_padding)
    int tagPadding;
    private Shots mShot;

    private LinearLayoutManager linearLayoutManager;
    private CommentsAdapter commentsAdapter;
    private
    @ColorInt
    int vibrantColor;


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
        initShots();
        initToolbar();
        initFab();
        initComments();
        initTag();
    }

    private void initTag() {
        //// FIXME: 4/16/16 make layout more compact
        ArrayList<String> tags = mShot.getTags();
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


    private void initShots() {
        if (TextUtils.isEmpty(mShot.getDescription())) {
            mShotsDescription.setText(AppData.getString(R.string.no_description));
        } else {
            mShotsDescription.setText(mShot.getDescription());
        }
        Picasso.with(this).load(Uri.parse(mShot.getUser().getAvatar_url()))
                .placeholder(AppData.getDrawable(R.drawable.avatar_default))
                .into(mAvatar);
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShotInfoActivity.this, ProfileActivity.class);
                intent.putExtra(ProfileActivity.USER, mShot.getUser());
                startActivity(intent);
            }
        });
        mShotsUser.setText(mShot.getUser().getName());
        mShotsTime.setText(String.format(AppData.getString(R.string.shots_time), CommonUtils.formatDate(mShot.getCreated_at())));
        mBucketCount.setText(String.format(AppData.getString(R.string.buckets), mShot.getBuckets_count()));
        mLikeCount.setText(String.format(AppData.getString(R.string.likes), mShot.getLikes_count()));
        mViewCount.setText(String.format(AppData.getString(R.string.views), mShot.getViews_count()));
        mCommentsCount.setText(String.format(AppData.getString(R.string.comments), mShot.getComments_count()));
    }

    private void initComments() {
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY != oldScrollY && mFabLayout.isToolbar()) mFabLayout.hide();
            }
        });
        commentsAdapter = new CommentsAdapter(this, R.layout.item_comments);
        commentsAdapter.setOnItemClickListener(new CommentsAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, Comments comments) {
                Intent intent = new Intent(ShotInfoActivity.this, ProfileActivity.class);
                intent.putExtra(ProfileActivity.USER, comments.getUser());
                startActivity(intent);
            }
        });

        mFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: show more comments in a new Activity.
                Intent intent = new Intent(ShotInfoActivity.this, ShotCommentActivity.class);
                intent.putExtra(ShotCommentActivity.COMMENTS_SHOTS, mShot);
                startActivity(intent);
            }
        });
        mComments.setAdapter(commentsAdapter);
        mComments.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        linearLayoutManager = new LinearLayoutManager(this);
        // Enable recyclerview scrolled by the wrapped scrollnestedview.
        mComments.setNestedScrollingEnabled(false);
        mComments.setLayoutManager(linearLayoutManager);
        DribbbleShotsService commentsService = ApiServiceFactory.createService(DribbbleShotsService.class, UserManager.getCurrentToken());
        Map<String, String> params = new HashMap<>();
        params.put("per_page", Integer.toString(COMMENTS_PER_PAGE));
        commentsService.getComment(mShot.getId(), params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Comments>>() {
                    @Override
                    public void onCompleted() {
                        mProgress.setVisibility(View.GONE);
                        mComments.setVisibility(View.VISIBLE);
                        if (Integer.parseInt(mShot.getComments_count()) > COMMENTS_PER_PAGE) {
                            mFooter.setVisibility(View.VISIBLE);
                        }
                        if (Integer.parseInt(mShot.getComments_count())  == 0) {
                            mFooter.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.wtf("driclient", e);
                    }

                    @Override
                    public void onNext(List<Comments> commentses) {
                        commentsAdapter.setDataAfter(commentses);

                    }
                });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Disable the title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Log.i("driclient", "mShot image is " + mShot.getImages().getJson());
        Picasso.with(this)
                .load(Uri.parse(mShot.getImages().getNormal()))
                .into(mImageView);
        extractColor();
        mImageView.setOnClickListener(new ShowImageClicker());

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
                    toolbarLayout.setTitle(mShot.getTitle());
                    isShow = true;
                } else if (isShow) {
                    toolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
    }

    // extract Color from the loaded image
    private void extractColor() {
        if (CommonUtils.isGif(mShot)) {
            mImageView.setColorFilter(CommonUtils.brightIt(-100));
            mImageView.setClickable(false);
            mPlay.setVisibility(View.VISIBLE);
            mPlay.setOnClickListener(new ShowImageClicker());
        }
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
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFabLayout.show();

            }
        });
        mAddBuckets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFabLayout.hide();
            }
        });
        mAddLike.setOnClickListener(new ShotLikeClickListener(mShot) {
            @Override
            public void isLike() {
                mAddLike.setImageDrawable(AppData.getDrawable(R.drawable.ic_favorite_white_36dp));
            }

            @Override
            public void onLike() {
                showMessage("Like");
            }

            @Override
            public void onUnlike() {
                showMessage("Undo like");
            }

            @Override
            public void onPreLike() {
                mAddLike.setImageDrawable(AppData.getDrawable(R.drawable.ic_favorite_white_36dp));
                mFabLayout.hide();
            }

            @Override
            public void onPreUnlike() {
                mAddLike.setImageDrawable(AppData.getDrawable(R.drawable.ic_favorite_border_white_36dp));
                mFabLayout.hide();
            }
        });
        mAddShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFabLayout.hide();
            }
        });

        mAddComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFabLayout.hide();
            }
        });
    }


    private class ShowImageClicker implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ShotInfoActivity.this, ImageDetailActivity.class);
            intent.putExtra(ImageDetailActivity.SHARED_SHOTS, mShot);
            startActivity(intent);
        }
    }


}
