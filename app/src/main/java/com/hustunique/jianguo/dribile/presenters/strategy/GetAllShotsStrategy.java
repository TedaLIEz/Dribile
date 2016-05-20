package com.hustunique.jianguo.dribile.presenters.strategy;

import com.hustunique.jianguo.dribile.dao.ObservableShotsDb;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.service.DribbbleShotsService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by JianGuo on 5/5/16.
 * Strategy for loading all shots
 */
public class GetAllShotsStrategy implements ILoadListDataStrategy<Shots>, ICacheDataStrategy<Shots> {
    private ObservableShotsDb mShots;

    public GetAllShotsStrategy() {
        mShots = new ObservableShotsDb();
    }
    @Override
    public Observable<List<Shots>> loadData(Map<String, String> params) {
        return ApiServiceFactory.createService(DribbbleShotsService.class)
                .getAllShots(params);
    }


    @Override
    public Observable<List<Shots>> loadFromDB() {
        Observable<List<Shots>> observable = mShots.getObservable();
        observable.unsubscribeOn(Schedulers.computation());
        return observable;
    }

    @Override
    public void cacheNew(List<Shots> datas) {
        mShots.insertShotList(datas);
    }

    @Override
    public void cacheMore(List<Shots> datas) {
        mShots.addShotList(datas);
    }
}
