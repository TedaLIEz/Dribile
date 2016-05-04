package com.hustunique.jianguo.driclient.views;

import com.hustunique.jianguo.driclient.models.Buckets;

import java.util.List;

/**
 * Created by JianGuo on 5/4/16.
 */
public interface ILoadListView<M> {
    void showEmpty();
    void showLoading();
    void onError(Exception e);
    void showData(List<M> bucketsList);
}
