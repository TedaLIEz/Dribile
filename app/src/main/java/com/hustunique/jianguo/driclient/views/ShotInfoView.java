package com.hustunique.jianguo.driclient.views;

import com.hustunique.jianguo.driclient.models.User;

/**
 * Created by JianGuo on 5/6/16.
 */
public interface ShotInfoView extends ShotView {
    void setDescription(String description);
    void setTime(String time);
    void setBucketCount(String bucketCount);
    void setUserName(String userName);
    void goToProfile(User user);
}
