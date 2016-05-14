package com.hustunique.jianguo.driclient.presenters.strategy;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 5/14/16.
 */
public interface ILoadDataStrategy<T> {
    Observable<T> loadData(Map<String, String> params);

}
