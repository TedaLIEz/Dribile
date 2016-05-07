package com.hustunique.jianguo.driclient.presenters.strategy;

import com.hustunique.jianguo.driclient.models.Comments;
import com.hustunique.jianguo.driclient.service.DribbbleShotsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 5/6/16.
 * Loading comments by shot's id.
 */
public class GetCommentsByIdStrategy implements ILoadDataStrategy<Comments> {
    private final String id;

    private Map<String, String> params;
    public GetCommentsByIdStrategy(String id) {
        this.id = id;
    }



    @Override
    public Observable<List<Comments>> loadData(Map<String, String> params) {
        return ApiServiceFactory.createService(DribbbleShotsService.class)
                .getComment(id, params);
    }
}
