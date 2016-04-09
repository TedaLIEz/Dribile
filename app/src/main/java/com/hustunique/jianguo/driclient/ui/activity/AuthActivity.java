package com.hustunique.jianguo.driclient.ui.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.am.AccountGeneral;
import com.hustunique.jianguo.driclient.app.MyApp;
import com.hustunique.jianguo.driclient.bean.AccessToken;
import com.hustunique.jianguo.driclient.bean.User;
import com.hustunique.jianguo.driclient.service.DribbbleAuthService;
import com.hustunique.jianguo.driclient.service.DribbbleUserService;
import com.hustunique.jianguo.driclient.service.api.Constants;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.driclient.service.factories.AuthServiceFactory;
import com.hustunique.jianguo.driclient.ui.widget.OAuthWebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Activity for auth including a webView
 */
public class AuthActivity extends AccountAuthenticatorActivity implements OAuthWebView.IAuth {
    public static final String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public static final String ARG_AUTH_TYPE = "AUTH_TYPE";
    public static final String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public static final String AUTH_USER = "AUTH_USER";
    public static final int AUTH_DENIED = 0x11111111;
    public static final int AUTH_FAILED = 0x11111110;
    public static final String ERR_AUTH_MSG = "ERR_AUTH_MSG";
    public static final String TOKEN = "TOKEN";
    @Bind(R.id.view_auth)
    OAuthWebView webView;

    private AccountManager mAccountManager;

    private String scope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        initView();
        // This activity may start in the accounts settings, so first get the intent's data.
        scope = getIntent().getStringExtra(ARG_AUTH_TYPE);
        if (scope == null) {
            scope = AccountGeneral.AUTHTOKEN_TYPE_READ_ONLY;
        }
        mAccountManager = AccountManager.get(this);
    }

    private void initView() {
        initWebView();
    }

    private void initWebView() {
        webView.allowCookies(false);
        webView.loadUrl(Constants.OAuth.URL_BASE_OAUTH + "authorize", MyApp.redirect_url, MyApp.client_id, scope);
        webView.setAuthListener(this);
    }

    @Override
    public void onAuth(Uri uri) {
        parseToken(uri);
    }

    private void parseToken(Uri uri) {
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
                            onAuthFailed(e);
                        }

                        @Override
                        public void onNext(AccessToken token) {
                            Log.i("driclient", "successfully get token -> " + token.toString()
                                    + "\n scope -> " + token.getScope());

                            onAuthSuccess(token);
                        }
                    });
        } else if (uri.getQueryParameter("error") != null) {
            setResult(AUTH_DENIED);
            finish();
        }
    }



    private void onAuthSuccess(@NonNull final AccessToken token) {
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
                        onAuthFailed(e);
                    }

                    @Override
                    public void onNext(User user) {
                        // get account type
                        String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
                        if (accountType == null) {
                            accountType = AccountGeneral.ACCOUNT_TYPE;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, user.getName());
                        bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                        bundle.putString(AccountManager.KEY_AUTHTOKEN, token.toString());
                        bundle.putString(ARG_AUTH_TYPE, token.getScope());
                        bundle.putSerializable(AUTH_USER, user);
                        bundle.putSerializable(TOKEN, token);
                        Intent intent = new Intent();
                        intent.putExtras(bundle);
                        finishLogin(intent);
                    }
                });

    }

    private void finishLogin(Intent intent) {
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        String authToken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
        String authTokenType = intent.getStringExtra(ARG_AUTH_TYPE);
        // Can't get password in this case, just set it to null.
        mAccountManager.addAccountExplicitly(account, null, null);
        mAccountManager.setAuthToken(account, authTokenType, authToken);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onAuthFailed(Throwable e) {
        Intent intent = new Intent();
        intent.putExtra(ERR_AUTH_MSG, e.getMessage());
        setResult(AUTH_FAILED, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
