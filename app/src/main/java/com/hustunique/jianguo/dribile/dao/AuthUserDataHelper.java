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

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.OAuthUser;
import com.hustunique.jianguo.dribile.dao.sql.BaseColumns;
import com.hustunique.jianguo.dribile.dao.sql.Column;
import com.hustunique.jianguo.dribile.dao.sql.SQLiteTable;

/**
 * Created by JianGuo on 4/1/16.
 * Database helper for {@link OAuthUser}
 */
@Deprecated
public class AuthUserDataHelper extends BasicDataHelper {

    public AuthUserDataHelper() {
        super(AppData.getContext());
    }

    private ContentValues getContentValues(OAuthUser oAuthUser) {
        ContentValues values = new ContentValues();
        values.put(AuthUserTable.ACCESS_TOKEN, oAuthUser.getAccessToken().getAccess_token());
        values.put(AuthUserTable.NAME, oAuthUser.getUser().getName());
        values.put(AuthUserTable.JSON, oAuthUser.getJson());

        return values;
    }

    @Override
    protected Uri getContentUri() {
        return DataProvider.UATH_USER_CONTENT_URI;
    }


    /**
     * query for the current AuthUser
     * @return the {@link OAuthUser}
     */
    public OAuthUser query() {
        Cursor cursor = getList(null, null, null, null);
        return OAuthUser.fromCursor(cursor);
    }


    /**
     * save an {@link OAuthUser}
     * @param oAuthUser the authUser
     * @return the URL of the newly created row.
     */
    public Uri save(OAuthUser oAuthUser) {
        ContentValues values = getContentValues(oAuthUser);
        if (!isAuthUserExit()) {
            update(oAuthUser);
            return DataProvider.UATH_USER_CONTENT_URI;
        }
        return insert(values);
    }

    /**
     * Update a current authUser
     * @param oAuthUser the authUser
     * @return the number of rows updated
     */
    public int update(OAuthUser oAuthUser) {
        ContentValues values = getContentValues(oAuthUser);
        return update(values, AuthUserTable.NAME + "=?", new String[] {
                oAuthUser.getUser().getName()
        });
    }


    public int deleteAll() {
        synchronized (DataProvider.DBLock) {
            DataProvider.DBHelper mDBHelper = DataProvider.getDBHelper();
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            int row = db.delete(AuthUserDataHelper.AuthUserTable.TABLE_NAME, null, null);
            return row;
        }
    }

    // Table for authUsers
    public static class AuthUserTable implements BaseColumns {
        public static String TABLE_NAME = "auth_user";

        public static String ACCESS_TOKEN = "access_token";

        public static String NAME = "name";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ACCESS_TOKEN, Column.DataType.TEXT).addColumn(NAME, Column.DataType.TEXT)
                .addColumn(JSON, Column.DataType.TEXT);
    }


    /**
     * return true if user not exit
     * @return <tt>true</tt> if user exits, <tt>false</tt> if doesn't.
     */
    public boolean isAuthUserExit() {
        Cursor cursor = getList(null, null, null, null);
        if (cursor.getCount() == 0) {
            return true;
        } else {
            return false;
        }
    }

}
