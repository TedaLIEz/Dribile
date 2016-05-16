package com.hustunique.jianguo.driclient.presenters.strategy;

import com.hustunique.jianguo.driclient.dao.ObservableShotsDb;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleShotsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;

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
