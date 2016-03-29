package com.hustunique.jianguo.driclient.bean;

/**
 * Created by JianGuo on 3/29/16.
 * POJO for user with API-Key
 */
public class OAuthUser {
    private User mUser;

    private ApiKey mKey;

    public OAuthUser(){}

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    public ApiKey getmKey() {
        return mKey;
    }

    public void setmKey(ApiKey mKey) {
        this.mKey = mKey;
    }
}
