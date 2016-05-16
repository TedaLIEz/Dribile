package com.hustunique.jianguo.driclient.presenters.strategy;


import java.util.List;

import rx.Observable;

/**
 * Created by JianGuo on 5/8/16.
 * Interface for caching datas
 */
public interface ICacheDataStrategy<T> {
    Observable<List<T>> loadFromDB();

    void cacheNew(List<T> datas);
    void cacheMore(List<T> datas);
}
