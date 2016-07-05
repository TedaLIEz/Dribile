package com.hustunique.jianguo.dribile.am;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.AccessToken;
import com.hustunique.jianguo.dribile.models.OAuthUser;
import com.hustunique.jianguo.dribile.models.User;
import com.hustunique.jianguo.dribile.service.DribbbleUserService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;

import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by JianGuo on 5/27/16.
 * Account Manager for dribbble user
 */
public class MyAccountManager {
    private static final String USER_DATA = "USER_DATA";
    private static final String USER_TOKEN = "USER_TOKEN";
    private static AccountManager accountManager = AccountManager.get(AppData.getContext());
    private static Account currAccount;
    private static OAuthUser currUser;


    public static void addAccount(Account account, OAuthUser user) {
        Bundle extras = new Bundle();

        extras.putString(USER_DATA, user.getUser().getJson());
        extras.putString(USER_TOKEN, user.getAccessToken().getJson());
        accountManager.addAccountExplicitly(account, null, extras);
        accountManager.setAuthToken(account, user.getAccessToken().getScope(), user.getAccessToken().toString());
        currAccount = account;
    }

    public static void setCurrentUser(OAuthUser oAuthUser) {
        currUser = oAuthUser;
    }

    public static OAuthUser getCurrentUser() {
        if (hasAccount()) {
            currAccount = getAccounts()[0];
            String userData = accountManager.getUserData(currAccount, USER_DATA);
            String token = accountManager.getUserData(currAccount, USER_TOKEN);
            Gson gson = new Gson();
            User user = gson.fromJson(userData, User.class);
            AccessToken accessToken = gson.fromJson(token, AccessToken.class);
            currUser = new OAuthUser();
            currUser.setAccessToken(accessToken);
            currUser.setUser(user);
        }
        return currUser;
    }

    public static AccessToken getCurrentToken() {
        return currUser.getAccessToken();
    }


    public static void removeAccount() {
        accountManager.removeAccount(currAccount, null, null);
        currUser = null;
    }

    private static boolean hasAccount() {
        return accountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE).length != 0;
    }

    private static Account[] getAccounts() {
        return accountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
    }

    public static void updateUser() {
        DribbbleUserService dribbbleUserService = ApiServiceFactory.createService(DribbbleUserService.class);
        dribbbleUserService.getAuthUser().observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
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
                        String userData = user.getJson();
                        accountManager.setUserData(currAccount, USER_DATA, userData);
                    }
                });
    }
}