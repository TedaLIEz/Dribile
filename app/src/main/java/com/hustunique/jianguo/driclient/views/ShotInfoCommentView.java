package com.hustunique.jianguo.driclient.views;

import com.hustunique.jianguo.driclient.models.Comments;
import com.hustunique.jianguo.driclient.models.Shots;

/**
 * Created by JianGuo on 5/7/16.
 */
public interface ShotInfoCommentView extends  ILoadListView<Comments> {
    void goToMoreComments(Shots shots);
}
