package com.hustunique.jianguo.dribile.views;

import android.content.Intent;

import com.hustunique.jianguo.dribile.models.User;

/**
 * Created by JianGuo on 5/8/16.
 */
public interface ProfileView {
    void setName(String name);
    void setLocation(String location);
    void setBio(String bio);
    void setFollowerCount(String followerCount);
    void setFollowingCount(String followingCount);
    void setShotCount(String shotCount);
    void setLikeCount(String likeCount);
    void goToTwitter(String twitterUri);
    void goToDribbble(Intent intent);
    void followed();
    void unfollowed();
    void noTwitter();
    void setAvatar(String uri);
    void initFollow(boolean self);

    void goToShotList(User model);

    void goToLikeList(User model);
}
