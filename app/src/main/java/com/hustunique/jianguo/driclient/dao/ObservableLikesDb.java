package com.hustunique.jianguo.driclient.dao;

import android.database.Cursor;

import com.google.gson.Gson;
import com.hustunique.jianguo.driclient.models.Shots;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

/**
 * Created by JianGuo on 5/16/16.
 * Helper for Shots in Observable
 */
public class ObservableLikesDb {
    private LikesDataHelper mHelper;
//    private PublishSubject<List<Shots>> mSubject = PublishSubject.create();

    public ObservableLikesDb() {
        mHelper = new LikesDataHelper();
    }

    private List<Shots> getAllFromDB() {
        Gson gson = new Gson();

        Cursor cursor = mHelper.getList();
        cursor.moveToFirst();
        List<Shots> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            data.add(gson.fromJson(
                    cursor.getString(cursor.getColumnIndex(LikesDataHelper.ShotsTable.JSON)),
                    Shots.class));
        }
        cursor.close();
        return data;
    }


    public void addShotList(List<Shots> list) {
        for (Shots shots : list) {
            mHelper.insert(shots);
        }
//        mSubject.onNext(list);
    }


    public void insertShotList(List<Shots> list) {
        mHelper.deleteAll();
        for (Shots shots : list) {
            mHelper.insert(shots);
        }
//        mSubject.onNext(list);
    }

    public Observable<List<Shots>> getObservable() {
        Observable<List<Shots>> observable = rx.Observable.fromCallable(new Callable<List<Shots>>() {
            @Override
            public List<Shots> call() throws Exception {
                return getAllFromDB();
            }
        });
        return observable;
    }
}
