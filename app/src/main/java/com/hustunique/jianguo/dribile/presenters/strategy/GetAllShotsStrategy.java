/*
 * Copyright (c) 2016.  TedaLIEz <aliezted@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hustunique.jianguo.dribile.presenters.strategy;

import com.hustunique.jianguo.dribile.dao.ObservableShotsDb;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.service.endpoint.DribbbleShotsService;
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
