package com.hustunique.jianguo.driclient.ui.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
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
import com.hustunique.jianguo.driclient.service.DribbbleAuthService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.driclient.service.api.Constants;
import com.hustunique.jianguo.driclient.service.factories.AuthServiceFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {
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
            Log.i("driclient", code);
            DribbbleAuthService authService = AuthServiceFactory.createAuthService(DribbbleAuthService.class);
            Call<AccessToken> call = authService.getAccessToken(MyApp.client_id, MyApp.client_secret, code);
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Log.i("driclient", response.body() + "");
                    //TODO: Save token or save the auth user instead
                    AuthActivity.this.finish();
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.e("driclient", "error" + t.getMessage());
                }
            });
        } else if (uri.getQueryParameter("error") != null) {
            Log.e("driclient", "error when get token");
            loginFail();
        }
    }

    //TODO: login failed
    private void loginFail() {

    }


}
