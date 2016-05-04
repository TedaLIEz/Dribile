package com.hustunique.jianguo.driclient.views;


import com.hustunique.jianguo.driclient.models.Shots;

/**
 * Created by JianGuo on 5/3/16.
 */
public interface ShotView {
    void setShotTitle(String title);
    void setViewCount(String viewCount);
    void setCommentCount(String commentCount);
    void setLikeCount(String likeCount);
    void setAnimated(boolean animated);
    void setShotImage(String imageUrl);
    void setAvatar(String avatar_url);
    void goToDetailView(Shots model);
    void hideAvatar();
}
