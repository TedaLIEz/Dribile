package com.hustunique.jianguo.dribile.ui.activity;

import android.accounts.AccountAuthenticatorActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.am.AccountGeneral;
import com.hustunique.jianguo.dribile.app.MyApp;
import com.hustunique.jianguo.dribile.app.PresenterManager;
import com.hustunique.jianguo.dribile.presenters.AuthPresenter;
import com.hustunique.jianguo.dribile.service.api.Constants;
import com.hustunique.jianguo.dribile.ui.widget.OAuthWebView;
import com.hustunique.jianguo.dribile.views.AuthView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Activity for auth including a webView
 */
public class AuthActivity extends AccountAuthenticatorActivity implements AppCompatCallback, AuthView {
    @Bind(R.id.view_auth)
    OAuthWebView webView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private AppCompatDelegate delegate;
    private String scope;

    private ProgressDialog mProgressDialog;

    private AuthPresenter mAuthPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mAuthPresenter = new AuthPresenter();
        } else {
            mAuthPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
        delegate = AppCompatDelegate.create(this, this);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        delegate.setSupportActionBar(mToolbar);
        delegate.getSupportActionBar().setTitle("Log in to Dribbble");
        delegate.getSupportActionBar().setDisplayShowTitleEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        // This activity may start in the accounts settings, so first get the intent's data.
        scope = getIntent().getStringExtra(AuthPresenter.ARG_AUTH_TYPE);
        if (scope == null || TextUtils.isEmpty(scope)) {
            scope = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
        }
        initView();
//        mAccountManager = AccountManager.get(this);
    }

    private void initView() {
        initWebView();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Login to Dribbble");

    }

    private void initWebView() {
        webView.allowCookies(false);
        webView.loadUrl(Constants.OAuth.URL_BASE_OAUTH + "authorize", MyApp.redirect_url, MyApp.client_id, scope);
        webView.setAuthListener(new OAuthWebView.IAuth() {
            @Override
            public void onAuth(Uri uri) {
                mProgressDialog.show();
                mAuthPresenter.parseToken(uri);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuthPresenter.bindView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAuthPresenter.unbindView();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mAuthPresenter, outState);
    }


    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Override
    public void onError(Throwable e) {
        Intent intent = new Intent();
        intent.putExtra(AuthPresenter.ERR_AUTH_MSG, e.getMessage());
        setResult(AuthPresenter.AUTH_FAILED, intent);
        finish();
    }

    @Override
    public AuthActivity getRef() {
        return this;
    }

    @Override
    public void onSuccess() {
        mProgressDialog.dismiss();
    }
}
