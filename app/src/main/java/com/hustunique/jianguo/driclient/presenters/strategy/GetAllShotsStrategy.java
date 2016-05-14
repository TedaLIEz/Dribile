package com.hustunique.jianguo.driclient.presenters.strategy;

import android.database.Cursor;

import com.google.gson.Gson;
import com.hustunique.jianguo.driclient.dao.ShotsDataHelper;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleShotsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 5/5/16.
 * Strategy for loading all shots
 */
public class GetAllShotsStrategy implements ILoadListDataStrategy<Shots>, ICacheDataStrategy<Shots> {

    @Override
    public Observable<List<Shots>> loadData(Map<String, String> params) {
        return ApiServiceFactory.createService(DribbbleShotsService.class)
                .getAllShots(params);
    }


    @Override
    public List<Shots> loadFromDB() {
        Gson gson = new Gson();
        ShotsDataHelper helper = new ShotsDataHelper();
        Cursor cursor = helper.getList();
        cursor.moveToFirst();
        List<Shots> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            data.add(gson.fromJson(
                    cursor.getString(cursor.getColumnIndex(ShotsDataHelper.ShotsTable.JSON)),
                    Shots.class));
        }
        return data;
    }

    @Override
    public boolean cache(List<Shots> datas) {
        if (datas != null && datas.size() != 0) {
            ShotsDataHelper helper = new ShotsDataHelper();
            helper.deleteAll();
            helper.bulkInsert(datas);
            return true;
        }
        return false;
    }
}
