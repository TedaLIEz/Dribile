package com.hustunique.jianguo.driclient.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.bean.User;
import com.hustunique.jianguo.driclient.utils.NetUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity {
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
    private User mUser;
    public  static final String USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        mUser = (User) getIntent().getSerializableExtra(USER);
        if (mUser == null) {
            throw new IllegalArgumentException("you must give a user to show profile");
        }
        Log.i("driclient", "user " + mUser.getJson());
        initView();
    }

    //TODO: Show shots in this user
    private void initView() {
        Picasso.with(this).load(Uri.parse(mUser.getAvatar_url())).placeholder(R.drawable.avatar_default).into(mAvatar);
        mName.setText(mUser.getName());
        mLocation.setText(mUser.getLocation());
        mBio.setText(mUser.getBio());
        mShotCount.setText(mUser.getShots_count());
        mFollowersCount.setText(mUser.getFollowers_count());
        mFollowingsCount.setText(mUser.getFollowings_count());
        mLikeCount.setText(mUser.getLikes_count());
        mTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser.getLink() != null && mUser.getLink().getTwitter() != null) {
                    startTwitterIntent(NetUtils.getNameFromTwitterUrl(mUser.getLink().getTwitter()));
                } else {
                    showMessage("Wops! He didn't leave his twitter here");
                }
            }
        });

        mDribbble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUser.getHtml_url()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        mFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Add PUT Method to follow this user
            }
        });

    }
}
