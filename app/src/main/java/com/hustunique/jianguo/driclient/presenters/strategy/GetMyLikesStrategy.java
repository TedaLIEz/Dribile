package com.hustunique.jianguo.driclient.presenters.strategy;

import android.database.Cursor;

import com.google.gson.Gson;
import com.hustunique.jianguo.driclient.dao.LikesDataHelper;
import com.hustunique.jianguo.driclient.dao.ShotsDataHelper;
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
 * Strategy for loading my likes
 */
public class GetMyLikesStrategy implements ILoadDataStrategy<Shots>, ICacheDataStrategy<Shots> {

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
    public List<Shots> loadFromDB() {
        LikesDataHelper likesDataHelper = new LikesDataHelper();
        Gson gson = new Gson();
        Cursor cursor = likesDataHelper.getList();
        cursor.moveToFirst();
        List<Shots> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            data.add(gson.fromJson(
                    cursor.getString(cursor.getColumnIndex(LikesDataHelper.ShotsTable.JSON)),
                    Shots.class));
        }
        return data;
    }

    @Override
    public boolean cache(List<Shots> datas) {
        if (datas != null && datas.size() != 0) {
            LikesDataHelper helper = new LikesDataHelper();
            helper.deleteAll();
            helper.bulkInsert(datas);
            return true;
        }
        return false;
    }
}
