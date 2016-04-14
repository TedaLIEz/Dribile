package com.hustunique.jianguo.driclient.ui.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.audiofx.LoudnessEnhancer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.app.UserManager;
import com.hustunique.jianguo.driclient.bean.Attachment;
import com.hustunique.jianguo.driclient.bean.Comments;
import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleLikeService;
import com.hustunique.jianguo.driclient.service.DribbbleShotsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.driclient.service.factories.ResponseBodyFactory;
import com.hustunique.jianguo.driclient.ui.adapters.AttachmentsAdapter;
import com.hustunique.jianguo.driclient.ui.adapters.CommentsAdapter;
import com.hustunique.jianguo.driclient.ui.widget.DividerItemDecoration;
import com.hustunique.jianguo.driclient.ui.widget.HTMLTextView;
import com.hustunique.jianguo.driclient.ui.widget.PaddingItemDecoration;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ShotInfoActivity extends BaseActivity {
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

    @Bind(R.id.shots_description)
    HTMLTextView mShotsDescription;

    @Bind(R.id.shots_user)
    TextView mShotsUser;

    @Bind(R.id.shots_time)
    TextView mShotsTime;

    @Bind(R.id.avatar_shots)
    ImageView mAvatar;

    @Bind(R.id.recyclerview_attachments)
    RecyclerView mAttachments;

    @Bind(R.id.attachment_layout)
    LinearLayout mAttachmentsLayout;

    @Bind(R.id.attachments_count)
    TextView mAttachmentsCount;

    @Bind(R.id.comments_count)
    TextView mCommentsCount;

    @BindDimen(R.dimen.item_divider_size)
    int dividerSize;
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
        initAttachments();
    }

    private void initAttachments() {
        if (Integer.parseInt(mShot.getAttachments_count()) == 0) {
            hideLayout();
            return;
        }
        mAttachmentsCount.setText(String.format(AppData.getString(R.string.attachments), mShot.getAttachments_count()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        final AttachmentsAdapter attachmentsAdapter = new AttachmentsAdapter(this, R.layout.item_attachment);
        mAttachments.addItemDecoration(new PaddingItemDecoration(dividerSize));
        mAttachments.setAdapter(attachmentsAdapter);
        mAttachments.setLayoutManager(linearLayoutManager);
        mAttachments.setHasFixedSize(false);
        mAttachments.setNestedScrollingEnabled(false);
        DribbbleShotsService dribbbleShotsService = ApiServiceFactory.createService(DribbbleShotsService.class, UserManager.getCurrentToken());
        dribbbleShotsService.getAttachments(mShot.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Attachment>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.wtf("client", e);
                    }

                    @Override
                    public void onNext(List<Attachment> attachments) {
                        attachmentsAdapter.setDataBefore(attachments);
                    }
                });
    }

    private void hideLayout() {
        mAttachmentsLayout.setVisibility(View.GONE);
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
    }

    private void initComments() {
        mCommentsCount.setText(String.format(AppData.getString(R.string.comments), mShot.getComments_count()));
        commentsAdapter = new CommentsAdapter(this, R.layout.item_comments);
        commentsAdapter.setOnItemClickListener(new CommentsAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, Comments comments) {
                Intent intent = new Intent(ShotInfoActivity.this, ProfileActivity.class);
                intent.putExtra(ProfileActivity.USER, comments.getUser());
                startActivity(intent);
            }
        });
        mComments.setAdapter(commentsAdapter);
        mComments.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        linearLayoutManager = new LinearLayoutManager(this);
        // Enable recyclerview scrolled by the wrapped scrollnestedview.
        mComments.setNestedScrollingEnabled(false);
        mComments.setHasFixedSize(false);
        mComments.setLayoutManager(linearLayoutManager);
        DribbbleShotsService commentsService = ApiServiceFactory.createService(DribbbleShotsService.class, UserManager.getCurrentToken());
        // By default you will only 12 comments via dribbble api
        Map<String, String> params = new HashMap<>();
        params.put("per_page", "50");
        commentsService.getComment(mShot.getId(), params)
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
        Log.i("driclient", "mShot image is " + mShot.getImages().getJson());

        //TODO: Load gif when clicks it, using shared element
        if (CommonUtils.isGif(mShot.getImages().getNormal())) {
            mImageView.setColorFilter(new LightingColorFilter(Color.GRAY, Color.GRAY));
        }

        Picasso.with(this)
                .load(Uri.parse(mShot.getImages().getNormal()))
                .into(mImageView);

        mImageView.setDrawingCacheEnabled(true);
        mImageView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mImageView.layout(0, 0,
                mImageView.getMeasuredWidth(), mImageView.getMeasuredHeight());
        mImageView.buildDrawingCache(true);
        Bitmap bmap = Bitmap.createBitmap(mImageView.getDrawingCache());
        Palette.from(bmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                vibrantColor = palette.getDarkVibrantColor(AppData.getColor(android.R.color.holo_blue_dark));
                toolbarLayout.setContentScrimColor(vibrantColor);
                //TODO: I hate you Google!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(vibrantColor);
                }
                toolbarLayout.setStatusBarScrimColor(vibrantColor);
            }
        });
        mImageView.setDrawingCacheEnabled(false);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShotInfoActivity.this, ImageDetailActivity.class);
                intent.putExtra(ImageDetailActivity.SHARED_IMAGE, mShot.getImages());
                startActivity(intent);
            }
        });

        // Correct way showing title only when collapsingToolbarLayout collapses, see http://stackoverflow.com/a/32724422/4380801
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            boolean isAnimated = false;

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
        //TODO: Add to my favourite
        DribbbleLikeService dribbbleLikeService = ResponseBodyFactory.createService(DribbbleLikeService.class, UserManager.getCurrentToken());
        dribbbleLikeService.isLike(mShot.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        boolean isLiked = false;
                        if (responseBodyResponse.code() == 200) {
                            mFab.setImageDrawable(AppData.getDrawable(R.drawable.ic_favorite_white_24dp));
                            isLiked = true;
                        }
                        mFab.setOnClickListener(new LikeClickListener(isLiked));
                    }
                });

    }

    //TODO: Extract this to a custom view
    class LikeClickListener implements View.OnClickListener {
        private boolean isLiked;
        public LikeClickListener(boolean isLiked) {
            this.isLiked = isLiked;
        }

        @Override
        public void onClick(View v) {
            DribbbleLikeService dribbbleLikeService = ResponseBodyFactory.createService(DribbbleLikeService.class, UserManager.getCurrentToken());
            if (isLiked) {
                dribbbleLikeService.unlike(mShot.getId()).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Response<ResponseBody>>() {
                            @Override
                            public void call(Response<ResponseBody> responseBodyResponse) {
                                if (responseBodyResponse.code() == 204) {
                                    mFab.setImageDrawable(AppData.getDrawable(R.drawable.ic_favorite_border_white_24dp));
                                    isLiked = false;
                                    Log.i("driclient", "unlike shot success");
                                } else {
                                    Log.e("driclient", "unlike shot failed" + responseBodyResponse.code());
                                }
                            }
                        });

            }  else {
                dribbbleLikeService.like(mShot.getId()).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Response<ResponseBody>>() {
                            @Override
                            public void call(Response<ResponseBody> responseBodyResponse) {
                                if (responseBodyResponse.code() == 201) {
                                    mFab.setImageDrawable(AppData.getDrawable(R.drawable.ic_favorite_white_24dp));
                                    isLiked = true;
                                    Log.i("driclient", "like shot success");
                                } else {
                                    Log.e("driclient", "like shot failed" + responseBodyResponse.code());
                                }
                            }
                        });
            }
        }
    }


}
