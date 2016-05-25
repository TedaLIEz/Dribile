package com.hustunique.jianguo.dribile.views;


import android.net.Uri;

import com.hustunique.jianguo.dribile.models.Shots;

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
    void setAvatar(Uri avatar_url);
    void goToDetailView(Shots model);
    void setDefaultAvatar();


}
