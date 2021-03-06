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

package com.hustunique.jianguo.dribile.models;

import android.database.Cursor;

import com.google.gson.Gson;
import com.hustunique.jianguo.dribile.dao.AuthUserDataHelper;

/**
 * Created by JianGuo on 3/29/16.
 * POJO for user with API-Key
 */
public class OAuthUser extends BaseBean {

    private User mUser;
    private AccessToken accessToken;

    public OAuthUser(){}

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public static OAuthUser fromCursor(Cursor cursor) {
        cursor.moveToFirst();
        return new Gson().fromJson(cursor.getString(cursor.getColumnIndex(AuthUserDataHelper.AuthUserTable.JSON)),
                OAuthUser.class);
    }

}
