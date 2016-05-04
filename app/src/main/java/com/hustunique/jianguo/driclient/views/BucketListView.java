package com.hustunique.jianguo.driclient.views;

import android.widget.ListView;

import com.hustunique.jianguo.driclient.models.Buckets;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by JianGuo on 5/3/16.
 */
public interface BucketListView extends IListView<Buckets>{

    void removeBucket(Buckets bucket);

    void createBucket(Buckets bucket);

    void addToBucket(Buckets bucket);

    void setTitle(String title);
}
