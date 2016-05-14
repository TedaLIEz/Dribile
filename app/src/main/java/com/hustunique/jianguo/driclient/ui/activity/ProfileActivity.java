package com.hustunique.jianguo.driclient.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.PresenterManager;
import com.hustunique.jianguo.driclient.models.User;
import com.hustunique.jianguo.driclient.presenters.ProfilePresenter;
import com.hustunique.jianguo.driclient.views.ProfileView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity implements ProfileView {
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
    public static final String USER = "user";
    private ProfilePresenter mProfilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent.getAction().equals(Intent.ACTION_VIEW)) {
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
        mTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfilePresenter.goToTwitter();
            }
        });

        mDribbble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfilePresenter.goToDribbble();
            }
        });
        mFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfilePresenter.onFollowClick();
            }
        });
        mShots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfilePresenter.goToShotList();
            }
        });
        mShots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfilePresenter.goToShotList();
            }
        });
        mLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfilePresenter.goToLikeList();
            }
        });


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
}
