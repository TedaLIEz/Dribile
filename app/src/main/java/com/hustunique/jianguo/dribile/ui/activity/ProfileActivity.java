package com.hustunique.jianguo.dribile.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.app.PresenterManager;
import com.hustunique.jianguo.dribile.models.User;
import com.hustunique.jianguo.dribile.presenters.ProfilePresenter;
import com.hustunique.jianguo.dribile.views.ProfileView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity implements ProfileView {
    private static final int POS_LOADED = 0;
    private static final int POS_LOADING = 1;
    @Bind(R.id.user_bio)
    TextView mBio;
    @Bind(R.id.user_followers_count)
    TextView mFollowersCount;
    @Bind(R.id.user_followings_count)
    TextView mFollowingsCount;
    @Bind(R.id.user_location)
    TextView mLocation;
    @Bind(R.id.user_name)
    TextView mName;
    @Bind(R.id.user_shot_count)
    TextView mShotCount;
    @Bind(R.id.user_like_count)
    TextView mLikeCount;
    @Bind(R.id.user_avatar)
    CircleImageView mAvatar;
    @Bind(R.id.user_twitter)
    CircleImageView mTwitter;
    @Bind(R.id.user_dribbble)
    CircleImageView mDribbble;
    @Bind(R.id.btn_follow)
    Button mFollow;
    @Bind(R.id.profile_shots)
    LinearLayout mShots;
    @Bind(R.id.profile_likes)
    LinearLayout mLikes;
    @Bind(R.id.animator)
    ViewAnimator mAnimator;

    public static final String USER = "user";
    private ProfilePresenter mProfilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_VIEW)) {
            Uri uri = intent.getData();
            mProfilePresenter = new ProfilePresenter(uri.getLastPathSegment());
        } else {
            User user = (User) getIntent().getSerializableExtra(USER);
            if (user == null) {
                throw new IllegalArgumentException("you must give a user to show profile");
            }
            if (savedInstanceState == null) {
                mProfilePresenter = new ProfilePresenter(user);
            } else {
                mProfilePresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
            }
        }

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProfilePresenter.bindView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProfilePresenter.unbindView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mProfilePresenter, outState);
    }

    private void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(AppData.getColor(R.color.profile_dark));
        }
    }

    @OnClick(R.id.user_twitter)
    void goToTwitter() {
        mProfilePresenter.goToTwitter();
    }

    @OnClick(R.id.user_dribbble)
    void goToDribbble() {
        mProfilePresenter.goToDribbble();
    }

    @OnClick(R.id.btn_follow)
    void onFollowClick() {
        mProfilePresenter.onFollowClick();
    }

    @OnClick(R.id.profile_shots)
    void goToShotList() {
        mProfilePresenter.goToShotList();
    }

    @OnClick(R.id.profile_likes)
    void goToLikeList() {
        mProfilePresenter.goToLikeList();
    }

    @Override
    public void setName(String name) {
        mName.setText(name);
    }

    @Override
    public void setLocation(String location) {
        mLocation.setText(location);
    }

    @Override
    public void setBio(String bio) {
        mBio.setText(bio);
    }

    @Override
    public void setFollowerCount(String followerCount) {
        mFollowersCount.setText(followerCount);
    }

    @Override
    public void setFollowingCount(String followingCount) {
        mFollowingsCount.setText(followingCount);
    }

    @Override
    public void setShotCount(String shotCount) {
        mShotCount.setText(shotCount);
    }

    @Override
    public void setLikeCount(String likeCount) {
        mLikeCount.setText(likeCount);
    }

    @Override
    public void goToTwitter(String twitterUri) {
        startTwitterIntent(twitterUri);
    }

    @Override
    public void goToDribbble(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void followed() {
        mFollow.setText("FOLLOWED");
    }

    @Override
    public void unfollowed() {
        mFollow.setText("FOLLOW");
    }

    @Override
    public void noTwitter() {
        showMessage("Wops! He didn't leave his twitter here");
    }

    @Override
    public void setAvatar(String uri) {
        Picasso.with(this).load(Uri.parse(uri)).placeholder(R.drawable.avatar_default).into(mAvatar);
    }

    @Override
    public void initFollow(boolean self) {
        mFollow.setVisibility(self ? View.GONE : View.VISIBLE);
    }

    @Override
    public void goToShotList(User model) {
        Intent intent = new Intent(ProfileActivity.this, ShotListActivity.class);
        intent.putExtra(ShotListActivity.USER, model);
        startActivity(intent);
    }

    @Override
    public void goToLikeList(User model) {
        Intent intent = new Intent(this, LikeListActivity.class);
        intent.putExtra(LikeListActivity.USER, model);
        startActivity(intent);
    }

    @Override
    public void onError(Throwable throwable) {
        Log.wtf("driclient", throwable);
    }

    @Override
    public void showLoading() {
        mAnimator.setDisplayedChild(POS_LOADING);
    }

    @Override
    public void showData() {
        mAnimator.setDisplayedChild(POS_LOADED);
    }
}
