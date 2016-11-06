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
public class ObservableLikesDb {
    private LikesDataHelper mHelper;

    public ObservableLikesDb() {
        mHelper = new LikesDataHelper();
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
