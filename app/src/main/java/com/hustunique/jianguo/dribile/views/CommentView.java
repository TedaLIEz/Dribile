package com.hustunique.jianguo.dribile.views;

import com.hustunique.jianguo.dribile.models.Comments;

/**
 * Created by JianGuo on 5/4/16.
 * View for each comments item
 */
public interface CommentView {
    void setName(String name);
    void setBody(String body);
    void setTime(String time);
    void setLikeCount(String likeCount);
    void setAvatar(String avatar);

    void goToDetailView(Comments model);
}
