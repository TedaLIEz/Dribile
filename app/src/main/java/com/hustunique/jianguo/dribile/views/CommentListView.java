package com.hustunique.jianguo.dribile.views;

import android.net.Uri;

import com.hustunique.jianguo.dribile.models.Comments;

/**
 * Created by JianGuo on 5/6/16.
 * View for Comments ListView
 */
public interface CommentListView extends ILoadListView<Comments> {
    void onAddCommentSuccess(Comments comment);
    void onAddCommentError(Throwable e);
    void setTitle(String title);
    void setSubTitle(String subTitle);
    void setAvatar(Uri avatar_url);
}
