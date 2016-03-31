package com.hustunique.jianguo.driclient.bean;

/**
 * Created by JianGuo on 3/29/16.
 * POJO for user with API-Key
 */
public class OAuthUser {

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

}
