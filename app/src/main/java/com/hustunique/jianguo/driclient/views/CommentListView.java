package com.hustunique.jianguo.driclient.views;

import android.net.Uri;

import com.hustunique.jianguo.driclient.models.Comments;

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
