package com.hustunique.jianguo.dribile.presenters.strategy;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 5/5/16.
 * Strategy interface for loading data
 */
public interface ILoadListDataStrategy<T> {
    Observable<List<T>> loadData(Map<String, String> params);
}
