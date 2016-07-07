package com.hustunique.jianguo.dribile.views;

import com.hustunique.jianguo.dribile.models.Shots;

/**
 * Created by JianGuo on 7/5/16.
 */
public interface LikeListView extends ILoadListView<Shots>{
    void unlikeShot(int pos);

    void restoreShot(int pos, Shots removeShot);

    void showUndo(int pos);
}
