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
