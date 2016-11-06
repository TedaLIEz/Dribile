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

import com.hustunique.jianguo.dribile.dao.ObservableLikesDb;
import com.hustunique.jianguo.dribile.models.Likes;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.service.DribbbleUserService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by JianGuo on 5/5/16.
 * Strategy for loading my likes
 */
public class GetMyLikesStrategy implements ILoadListDataStrategy<Shots>, ICacheDataStrategy<Shots>  {
    private ObservableLikesDb mLikes;

    public GetMyLikesStrategy() {
        mLikes = new ObservableLikesDb();
    }

    @Override
    public Observable<List<Shots>> loadData(Map<String, String> params) {
        return ApiServiceFactory.createService(DribbbleUserService.class)
                .getAuthLikeShots(params)
                .map(new Func1<List<Likes>, List<Shots>>() {
                    @Override
                    public List<Shots> call(List<Likes> likes) {
                        List<Shots> rst = new ArrayList<Shots>();
                        for (Likes like : likes) {
                            rst.add(like.getShot());
                        }
                        return rst;
                    }
                });
    }

    @Override
    public Observable<List<Shots>> loadFromDB() {
        return mLikes.getObservable();
    }

    @Override
    public void cacheNew(List<Shots> datas) {
        mLikes.insertShotList(datas);
    }

    @Override
    public void cacheMore(List<Shots> datas) {
        mLikes.addShotList(datas);
    }
}
