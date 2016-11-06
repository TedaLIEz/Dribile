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

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.hustunique.jianguo.dribile.utils.Utils;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.views.ProfileView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity implements ProfileView {
    private static final int POS_LOADED = 0;
    private static final int POS_LOADING = 1;
    private static final String TAG = "ProfileActivity";
    @BindView(R.id.user_bio)
    TextView mBio;
    @BindView(R.id.user_followers_count)
    TextView mFollowersCount;
    @BindView(R.id.user_followings_count)
    TextView mFollowingsCount;
    @BindView(R.id.user_location)
    TextView mLocation;
    @BindView(R.id.user_name)
    TextView mName;
    @BindView(R.id.user_shot_count)
    TextView mShotCount;
    @BindView(R.id.user_like_count)
    TextView mLikeCount;
    @BindView(R.id.user_avatar)
    CircleImageView mAvatar;
    @BindView(R.id.user_twitter)
    CircleImageView mTwitter;
    @BindView(R.id.user_dribbble)
    CircleImageView mDribbble;
    @BindView(R.id.btn_follow)
    Button mFollow;
    @BindView(R.id.profile_shots)
    LinearLayout mShots;
    @BindView(R.id.profile_likes)
    LinearLayout mLikes;
    @BindView(R.id.animator)
    ViewAnimator mAnimator;


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
            User user = (User) getIntent().getSerializableExtra(Utils.EXTRA_USER);
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
        mFollow.setText(AppData.getString(R.string.followed));
    }

    @Override
    public void unfollowed() {
        mFollow.setText(AppData.getString(R.string.follow));
    }

    @Override
    public void noTwitter() {
        showMessage(AppData.getString(R.string.no_twitter));
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
        Utils.startActivityWithUser(this, ShotListActivity.class, model);
    }

    @Override
    public void goToLikeList(User model) {
        Utils.startActivityWithUser(this, LikeListActivity.class, model);
    }

    @Override
    public void onError(Throwable throwable) {
        Logger.wtf(TAG, throwable);
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
