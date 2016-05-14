package com.hustunique.jianguo.driclient.presenters.strategy;

import com.hustunique.jianguo.driclient.models.Likes;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleUserService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by JianGuo on 5/5/16.
 * Get user's likes
 */
public class GetLikesByIdStrategy implements ILoadListDataStrategy<Shots> {

    private final String id;

    public GetLikesByIdStrategy(String id) {
        this.id = id;
    }

    @Override
    public Observable<List<Shots>> loadData(Map<String, String> params) {
        return ApiServiceFactory.createService(DribbbleUserService.class)
                .getLikeShots(id, params)
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
}
