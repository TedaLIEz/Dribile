package com.hustunique.jianguo.driclient.views;

import com.hustunique.jianguo.driclient.models.Buckets;

/**
 * Created by JianGuo on 5/4/16.
 */
public interface BucketView extends IListView<Buckets> {
    void createBucket(Buckets bucket);
    void removeBucket(Buckets bucket);
}
