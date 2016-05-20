package com.hustunique.jianguo.dribile.presenters.strategy;

import com.hustunique.jianguo.dribile.models.User;
import com.hustunique.jianguo.dribile.service.DribbbleUserService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;

import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 5/14/16.
 * Strategy for loading a user by user's id
 */
public class GetUserByIdStrategy implements ILoadDataStrategy<User> {
    private final String id;

    public GetUserByIdStrategy(String id) {
        this.id = id;
    }

    @Override
    public Observable<User> loadData(Map<String, String> params) {
        return ApiServiceFactory.createService(DribbbleUserService.class).getUser(id);
    }
}
