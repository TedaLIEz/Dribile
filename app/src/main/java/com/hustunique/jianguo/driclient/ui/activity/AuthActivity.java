package com.hustunique.jianguo.driclient.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.MyApp;
import com.hustunique.jianguo.driclient.bean.AccessToken;
import com.hustunique.jianguo.driclient.bean.User;
import com.hustunique.jianguo.driclient.service.DribbbleAuthService;
import com.hustunique.jianguo.driclient.service.DribbbleUserService;
import com.hustunique.jianguo.driclient.service.api.Constants;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.driclient.service.factories.AuthServiceFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Activity for auth including a webView
 */
public class AuthActivity extends BaseActivity {
    public static final String TOKEN = "token";
    public static final String USER = "user";
    public static final String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public static final String ARG_AUTH_TYPE = "AUTH_TYPE";
    public static final String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";

    @Bind(R.id.wv_auth)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initWebView();
    }

    private void initWebView() {
        clearCookies();
        webView.setWebViewClient(new AuthClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(false);
        settings.setSavePassword(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.loadUrl(Constants.OAuth.URL_BASE_OAUTH + "authorize" +
                "?client_id=" + MyApp.client_id + "&redirect_uri=" + MyApp.redirect_url);
    }

    private void clearCookies() {
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeSessionCookie();
    }


    private class AuthClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (url.equals(MyApp.redirect_url)) {

            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (url.startsWith(MyApp.redirect_url)) {
                Uri uri = Uri.parse(url);
                parseToken(uri);
                view.stopLoading();
                view.loadUrl("about:blank");
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
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
                            onAuthSuccess(token);
                        }
                    });
        } else if (uri.getQueryParameter("error") != null) {
            onAuthFailed(new Throwable(uri.getQueryParameter("error")));
        }
    }



    public void onAuthSuccess(AccessToken token) {
        Intent intent = new Intent();
        intent.putExtra(TOKEN, token);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onAuthFailed(Throwable e) {

    }


}
