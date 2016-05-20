package com.hustunique.jianguo.dribile.presenters.strategy;

import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 5/14/16.
 */
public interface ILoadDataStrategy<T> {
    Observable<T> loadData(Map<String, String> params);

}
