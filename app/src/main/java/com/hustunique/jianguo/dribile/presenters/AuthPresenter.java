package com.hustunique.jianguo.dribile.presenters;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.hustunique.jianguo.dribile.am.AccountGeneral;
import com.hustunique.jianguo.dribile.app.MyApp;
import com.hustunique.jianguo.dribile.models.AccessToken;
import com.hustunique.jianguo.dribile.models.User;
import com.hustunique.jianguo.dribile.service.DribbbleAuthService;
import com.hustunique.jianguo.dribile.service.DribbbleUserService;
import com.hustunique.jianguo.dribile.service.api.Constants;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.dribile.service.factories.AuthServiceFactory;
import com.hustunique.jianguo.dribile.views.AuthView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JianGuo on 5/1/16.
 * Auth Presenter
 */
public class AuthPresenter extends BasePresenter<User, AuthView> {
    public static final String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public static final String ARG_AUTH_TYPE = "AUTH_TYPE";
    public static final String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public static final String AUTH_USER = "AUTH_USER";
    public static final int AUTH_DENIED = 0x11111111;
    public static final int AUTH_FAILED = 0x11111110;
    public static final int AUTH_CANCELED = 0x11111100;
    public static final String ERR_AUTH_MSG = "ERR_AUTH_MSG";
    public static final String TOKEN = "TOKEN";
    public static final int AUTH_OK = 0x11111101;

    private AccessToken token;
    private Account account;
    private String accountType;
    private String scope;
    private Intent intent;



    public AuthPresenter(String accountType, String scope) {
        if (accountType == null || TextUtils.isEmpty(accountType)) {
            accountType = AccountGeneral.ACCOUNT_TYPE;
        }
        if (scope == null || TextUtils.isEmpty(scope)) {
            scope = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
        }
        this.accountType = accountType;
        this.scope = scope;
    }

    private void loadUrl(String scope) {
        if (scope == null || TextUtils.isEmpty(scope)) {
            scope = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
        }
        view().loadUrl(Constants.OAuth.URL_BASE_OAUTH + "authorize", MyApp.redirect_url, MyApp.client_id, scope);
    }


    @Override
    public void bindView(@NonNull AuthView view) {
        super.bindView(view);
        loadUrl(scope);
    }

    public void parseToken(Uri uri) {
        String code = uri.getQueryParameter("code");
        if (code != null) {
            final DribbbleAuthService authService = AuthServiceFactory.
                    createAuthService(DribbbleAuthService.class);
            authService.getAccessToken(MyApp.client_id, MyApp.client_secret, code)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<AccessToken>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            view().onError(e);
                        }

                        @Override
                        public void onNext(AccessToken token) {
                            Log.i("driclient", "successfully get token -> " + token.toString()
                                    + "\n scope -> " + token.getScope());
                            AuthPresenter.this.token = token;
                            onAuthSuccess();
                        }
                    });
        } else if (uri.getQueryParameter("error") != null) {
            view().onLoginFailed(AUTH_DENIED);
        }
    }

    private void onAuthSuccess() {
        Log.i("driclient", "get token -> start get user");
        DribbbleUserService dribbbleUserService = ApiServiceFactory.createService(DribbbleUserService.class, token);
        dribbbleUserService.getAuthUser()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view().onError(e);
                    }

                    @Override
                    public void onNext(User user) {
                        // get account type


                        Bundle bundle = new Bundle();
                        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, user.getName());
                        bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                        bundle.putString(AccountManager.KEY_AUTHTOKEN, token.toString());
                        bundle.putString(ARG_AUTH_TYPE, token.getScope());
                        bundle.putSerializable(AUTH_USER, user);
                        bundle.putSerializable(TOKEN, token);
                        intent = new Intent();
                        // Can't get password in this case, just set it to null.
                        account = new Account(user.getName(), accountType);
//                        mAccountManager.addAccountExplicitly(account, null, null);
//                        mAccountManager.setAuthToken(account, token.getScope(), token.toString());
                        intent.putExtras(bundle);
                        setModel(user);

                    }
                });

    }

    @Override
    protected void updateView() {
        view().onSuccess(intent, AUTH_OK);
        view().addAccount(account, token.getScope(), token.toString());
    }
}
