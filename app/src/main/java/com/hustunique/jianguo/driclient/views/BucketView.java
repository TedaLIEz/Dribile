package com.hustunique.jianguo.driclient.views;

import android.app.usage.NetworkStats;

import com.hustunique.jianguo.driclient.models.Buckets;

/**
 * Created by JianGuo on 5/5/16.
 * View for bucket item
 */
public interface BucketView {
    void setCount(String count);
    void setName(String name);
    void goToDetailView(Buckets bucket);

    boolean onLongClick(Buckets model);
}
