package com.hustunique.jianguo.dribile.views;

import com.hustunique.jianguo.dribile.models.Buckets;

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
