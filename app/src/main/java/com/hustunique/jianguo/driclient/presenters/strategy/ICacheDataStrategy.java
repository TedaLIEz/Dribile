package com.hustunique.jianguo.driclient.presenters.strategy;

import java.util.List;

/**
 * Created by JianGuo on 5/8/16.
 * Interface for caching datas
 */
public interface ICacheDataStrategy<T> {
    List<T> loadFromDB();
    boolean cache(List<T> datas);
}
