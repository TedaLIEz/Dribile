package com.hustunique.jianguo.driclient.presenters.strategy;

import com.hustunique.jianguo.driclient.models.Shots;

import java.util.List;

import rx.Observable;

/**
 * Created by JianGuo on 5/5/16.
 */
public interface ILoadShotStrategy extends ILoadDataStrategy<Shots> {
    Observable<List<Shots>> loadMore();
    int getLoadingCount();
}
