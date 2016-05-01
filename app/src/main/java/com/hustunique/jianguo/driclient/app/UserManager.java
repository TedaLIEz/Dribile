package com.hustunique.jianguo.driclient.app;

import android.util.Log;

import com.hustunique.jianguo.driclient.models.AccessToken;
import com.hustunique.jianguo.driclient.models.OAuthUser;
import com.hustunique.jianguo.driclient.models.User;
import com.hustunique.jianguo.driclient.dao.AuthUserDataHelper;
import com.hustunique.jianguo.driclient.service.DribbbleUserService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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


    public static void updateUser() {
        DribbbleUserService dribbbleUserService = ApiServiceFactory.createService(DribbbleUserService.class);
        dribbbleUserService.getAuthUser().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.wtf("driclient", e);
                    }

                    @Override
                    public void onNext(User user) {
                        UserManager.user.setUser(user);
                        AuthUserDataHelper helper = new AuthUserDataHelper();
                        helper.update(UserManager.user);
                    }
                });
    }

    /**
     * Get the token of current user
     * @return the {@link AccessToken} of logged user
     */
    public static AccessToken getCurrentToken() {
        return user.getAccessToken();
    }
}
