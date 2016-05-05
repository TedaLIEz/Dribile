package com.hustunique.jianguo.driclient.presenters.strategy;

import com.hustunique.jianguo.driclient.models.BaseBean;

import java.util.List;

import rx.Observable;

/**
 * Created by JianGuo on 5/5/16.
 * Strategy interface for loading data
 */
public interface ILoadDataStrategy<T extends BaseBean> {
    Observable<List<T>> loadData();
}
