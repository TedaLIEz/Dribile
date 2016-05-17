package com.hustunique.jianguo.driclient.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.hustunique.jianguo.driclient.R;


/**
 * Created by JianGuo on 4/2/16.
 * Custom webView for oauth
 */
public class OAuthWebView extends FrameLayout {

    WebView authView;
    ProgressBar progressBar;
    private String url;
    private String redirectUrl;
    private String clientId;
    private String scope;
    private String state;

    private Context mContext;

    private IAuth mIAuth;
    private ProgressChangeListener mProgressChangeListener;

    public OAuthWebView(Context context) {
        this(context, null);
    }

    public OAuthWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OAuthWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        mContext = context;
        initView();
        initStyle(attrs, defStyleAttr);


    }


    public void setAuthListener(IAuth iAuth) {
        mIAuth = iAuth;
    }

    public void setProGressChangeListener(ProgressChangeListener progressChangeListener) {
        mProgressChangeListener = progressChangeListener;
    }

    private void initStyle(AttributeSet attrs, int defStyleAttr) {

    }

    private void initWebView() {
        authView.setWebViewClient(new AuthClient());
        authView.setWebChromeClient(new ProgressBarClient());
    }

    private void initView() {
        View root = inflate(mContext, R.layout.view_auth_web, this);
        FrameLayout container = (FrameLayout) root.findViewById(R.id.auth_web_layout);
        authView = (WebView) container.findViewById(R.id.wv_auth);
        progressBar = (ProgressBar) container.findViewById(R.id.auth_progress);
        initWebView();
        initProgressBar();
    }

    private void initProgressBar() {
        progressBar.setMax(100);
    }


    /**
     * Allow cookies
     *
     * @param allow <tt>true</tt> if allow save cookies
     */
    public void allowCookies(boolean allow) {
        clearCookies();
        if (!allow) {
            CookieSyncManager.createInstance(mContext);
            CookieSyncManager.getInstance().startSync();
            CookieManager.getInstance().removeSessionCookie();
        }
    }

    /**
     * Clear the current cookies
     */
    public void clearCookies() {
        WebSettings settings = authView.getSettings();
        settings.setSaveFormData(false);
        settings.setSavePassword(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    /**
     * Set progress manually
     *
     * @param value the progress percent, min is 0, max is 100
     */
    public void setProgress(@IntRange(from = 0, to = 100) int value) {
        progressBar.setProgress(value);
    }

    /**
     * Load url
     *
     * @param url         the authorize url
     * @param redirectUrl the redirectUrl
     * @param clientId    the client Id
     * @param scope       Scopes let you specify exactly what type of access you need.
     */
    public void loadUrl(@NonNull String url, @NonNull String redirectUrl, @NonNull String clientId, @Nullable String scope) {
        progressBar.setProgress(0);
        this.url = url;
        this.redirectUrl = redirectUrl;
        this.clientId = clientId;
        if (TextUtils.isEmpty(scope)) {
            authView.loadUrl(url + "?client_id=" + clientId + "&redirect_uri=" + redirectUrl);
        } else {
            this.scope = scope;
            authView.loadUrl(url + "?client_id=" + clientId + "&redirect_uri=" + redirectUrl + "&scope=" + scope);
        }

    }

    private class AuthClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (url.startsWith(redirectUrl)) {
                view.stopLoading();
                if (mIAuth != null) {
                    mIAuth.onAuth(Uri.parse(url));
                }
                view.loadUrl("about:blank");
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    private class ProgressBarClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == progressBar.getMax()) {
                progressBar.setVisibility(INVISIBLE);
            } else {
                progressBar.setVisibility(VISIBLE);
            }
            progressBar.setProgress(newProgress);
            if (mProgressChangeListener != null) {
                mProgressChangeListener.onProgressChanged(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    public interface IAuth {
        void onAuth(Uri uri);
    }

    public interface ProgressChangeListener {
        void onProgressChanged(int progress);
    }
}
