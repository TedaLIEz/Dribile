package com.hustunique.jianguo.dribile.dao;

import android.database.Cursor;

import com.google.gson.Gson;
import com.hustunique.jianguo.dribile.models.Shots;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

/**
 * Created by JianGuo on 5/16/16.
 * Helper for Shots in Observable
 */
public class ObservableShotsDb {
    private ShotsDataHelper mHelper;


    public ObservableShotsDb() {
        mHelper = new ShotsDataHelper();
    }

    private List<Shots> getAllFromDB() {
            Gson gson = new Gson();
            Cursor cursor = mHelper.getList();
            List<Shots> data = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    data.add(gson.fromJson(cursor.getString(cursor.getColumnIndex(ShotsDataHelper.ShotsTable.JSON)),
                            Shots.class));
                } while (cursor.moveToNext());
            }
            cursor.close();
            return data;
    }


    public void addShotList(List<Shots> list) {
        for (Shots shots : list) {
            mHelper.insert(shots);
        }
    }


    public void insertShotList(List<Shots> list) {
        mHelper.deleteAll();
        for (Shots shots : list) {
            mHelper.insert(shots);
        }
    }

    public Observable<List<Shots>> getObservable() {
        return Observable.fromCallable(new Callable<List<Shots>>() {
            @Override
            public List<Shots> call() throws Exception {
                return getAllFromDB();
            }
        });
    }


}
