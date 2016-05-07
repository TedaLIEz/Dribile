package com.hustunique.jianguo.driclient.views;

import android.content.Intent;

import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.models.User;

import java.util.ArrayList;

/**
 * Created by JianGuo on 5/6/16.
 * View shots for ShotInfoActivity
 */
public interface ShotInfoView extends ShotView {
    void setDescription(String description);
    void setTime(String time);
    void setBucketCount(String bucketCount);
    void setUserName(String userName);
    void goToProfile(User user);

    void addToBucket(Shots model);

    void setTags(ArrayList<String> tags);
    void onLike();
    void onUnlike();

    void sendSharedIntent(Intent sendIntent);

    void addComments(Shots model);
}
