package com.hustunique.jianguo.dribile.views;

import com.hustunique.jianguo.dribile.models.Comments;
import com.hustunique.jianguo.dribile.models.Shots;

/**
 * Created by JianGuo on 5/7/16.
 */
public interface ShotInfoCommentView extends  ILoadListView<Comments> {
    void goToMoreComments(Shots shots);
}
