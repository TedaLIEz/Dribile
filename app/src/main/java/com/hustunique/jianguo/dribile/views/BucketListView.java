package com.hustunique.jianguo.dribile.views;

import com.hustunique.jianguo.dribile.models.Buckets;

/**
 * Created by JianGuo on 5/4/16.
 * View for buckets ListView
 */
public interface BucketListView extends ILoadListView<Buckets> {
    void createBucket(Buckets bucket);
    void removeBucket(Buckets bucket);
}
