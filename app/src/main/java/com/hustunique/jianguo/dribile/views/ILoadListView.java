package com.hustunique.jianguo.dribile.views;

import java.util.List;

/**
 * Created by JianGuo on 5/4/16.
 * Base View for loading a list of data <tt>M</tt>
 */
public interface ILoadListView<M> {
    void showEmpty();
    void showLoading();
    void onError(Throwable e);
    void showData(List<M> bucketsList);

    void showLoadingMore();
}
