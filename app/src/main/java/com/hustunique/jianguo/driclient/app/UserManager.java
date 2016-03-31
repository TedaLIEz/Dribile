package com.hustunique.jianguo.driclient.app;

import com.hustunique.jianguo.driclient.bean.AccessToken;
import com.hustunique.jianguo.driclient.bean.OAuthUser;

/**
 * Created by JianGuo on 3/31/16.
 * manager for {@link OAuthUser}
 */
public class UserManager {
    private static OAuthUser user;

    /**
     * return the auth-user who already logged in.
     * @return the auth-user
     */
    public static OAuthUser getCurrentUser() {
        if (user == null) {

        }
        return user;
    }

    /**
     * set the current user
     * @param user the user
     */
    public static void setCurrentUser(OAuthUser user) {

    }

    /**
     * Get the token of current user
     * @return the token of logged user
     */
    public static AccessToken getCurrentToken() {
        return user.getAccessToken();
    }
}
