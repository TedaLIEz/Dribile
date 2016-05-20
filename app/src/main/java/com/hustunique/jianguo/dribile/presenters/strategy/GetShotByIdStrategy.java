package com.hustunique.jianguo.dribile.presenters.strategy;

import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.models.User;
import com.hustunique.jianguo.dribile.service.DribbbleUserService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by JianGuo on 5/5/16.
 * Strategy for loading shots by user id.
 */
public class GetShotByIdStrategy implements ILoadListDataStrategy<Shots> {
    private final String id;
    private final User user;
    public GetShotByIdStrategy(User user) {
        this.id = user.getId();
        this.user = user;
    }


    @Override
    public Observable<List<Shots>> loadData(Map<String, String> params) {
        return ApiServiceFactory.createService(DribbbleUserService.class)
                .getShots(id ,params)
                .map(new Func1<List<Shots>, List<Shots>>() {
                    @Override
                    public List<Shots> call(List<Shots> shotses) {
                        for (Shots shots : shotses) {
                            shots.setUser(user);
                        }
                        return shotses;
                    }
                });
    }
}
