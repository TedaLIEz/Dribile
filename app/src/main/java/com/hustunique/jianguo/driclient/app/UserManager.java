package com.hustunique.jianguo.driclient.app;

import com.hustunique.jianguo.driclient.bean.AccessToken;
import com.hustunique.jianguo.driclient.bean.OAuthUser;
import com.hustunique.jianguo.driclient.dao.AuthUserDataHelper;

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
            AuthUserDataHelper helper = new AuthUserDataHelper();
            if (helper.isAuthUserExit()) {
                user = helper.query();
            }
        }
        return user;
    }

    /**
     * set the current user
     * @param authUser the user
     */
    public static void setCurrentUser(OAuthUser authUser) {
        user = authUser;

        AuthUserDataHelper helper = new AuthUserDataHelper();
        helper.save(authUser);
    }

    /**
     * Get the token of current user
     * @return the {@link AccessToken} of logged user
     */
    public static AccessToken getCurrentToken() {
        return user.getAccessToken();
    }
}
