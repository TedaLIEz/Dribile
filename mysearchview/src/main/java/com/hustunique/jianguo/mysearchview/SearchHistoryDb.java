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

package com.hustunique.jianguo.mysearchview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JianGuo on 11/8/16.
 * Database table for {@link SearchHistory}
 */
public class SearchHistoryDb extends SQLiteOpenHelper {
    static final String SEARCH_HISTORY_TABLE = "search_history";
    static final String SEARCH_HISTORY_COLUMN_ID = "_id";
    static final String SEARCH_HISTORY_COLUMN_TEXT = "_text";
    static final String SEARCH_HISTORY_COLUMN_TIMES = "_times";

    private static final String DATABASE_NAME = "search_history_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_SEARCH_HISTORY = "CREATE TABLE IF NOT EXISTS "
            + SEARCH_HISTORY_TABLE + " ( "
            + SEARCH_HISTORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SEARCH_HISTORY_COLUMN_TEXT + " TEXT, "
            + SEARCH_HISTORY_COLUMN_TIMES + " INTEGER " + ");";

    public SearchHistoryDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SEARCH_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropAllTables(db);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void dropAllTables(SQLiteDatabase db) {
        dropTableIfExists(db);
    }

    private void dropTableIfExists(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + SEARCH_HISTORY_TABLE);
    }

}
