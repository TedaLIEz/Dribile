package com.hustunique.jianguo.driclient.views;

import com.hustunique.jianguo.driclient.models.Buckets;

/**
 * Created by JianGuo on 5/3/16.
 * View for BucketInShotActivity
 */
public interface BucketInShotListView extends ILoadListView<Buckets> {

    void removeBucket(Buckets bucket);

    void createBucket(Buckets bucket);

    void addToBucket(Buckets bucket);

    void setTitle(String title);
}
