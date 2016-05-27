package com.hustunique.jianguo.dribile.views;

import android.accounts.Account;
import android.content.Intent;

import com.hustunique.jianguo.dribile.models.AccessToken;
import com.hustunique.jianguo.dribile.models.OAuthUser;

/**
 * Created by JianGuo on 5/1/16.
 * View for AuthActivity
 */
public interface AuthView {
    void onError(Throwable e);
    void onLoginFailed(int resultCode);
    void onSuccess(Intent intent, int resultCode);
    void loadUrl(String url, String redirect_url, String client_id, String scope);
    void addAccount(Account account, OAuthUser user);
}
