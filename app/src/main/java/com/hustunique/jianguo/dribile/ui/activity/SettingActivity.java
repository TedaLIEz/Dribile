package com.hustunique.jianguo.dribile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.am.MyAccountManager;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.app.PresenterManager;
import com.hustunique.jianguo.dribile.presenters.SettingPresenter;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.views.SettingView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements SettingView {

    private static final int SETTING = 0x000000;
    private static final String TAG = "SettingActivity";
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.layout_clear)
    LinearLayout mClean;
    @Bind(R.id.layout_contact)
    LinearLayout mContact;

    @Bind(R.id.layout_about)
    LinearLayout mAbout;

    @Bind(R.id.layout_logout)
    LinearLayout mLogout;
    @Bind(R.id.layout_license)
    LinearLayout mLicense;

    @Bind(R.id.rootView)
    CoordinatorLayout rootView;

    private SettingPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
//        mToolbar.setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(AppData.getString(R.string.settings));
        if (savedInstanceState == null) {
            mPresenter = new SettingPresenter();
        } else {
            PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
    }

    @OnClick(R.id.layout_contact)
    void contactClick() {
        mPresenter.sendEmail();
    }


    @OnClick(R.id.layout_logout)
    void logoutAccount() {
        mPresenter.logout();
    }


    @OnClick(R.id.layout_about)
    void aboutClick() {
        mPresenter.about();
    }

    @OnClick(R.id.layout_clear)
    void clearClick() {
        mPresenter.clear();
    }

    @OnClick(R.id.layout_license)
    void licenseClick() {
        mPresenter.license();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mPresenter, outState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.bindView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unbindView();
    }

    @Override
    public void onBackPressed() {
        setResult(SETTING);
        finish();
    }

    @Override
    public void onClearSuccess() {
        Snackbar.make(rootView, AppData.getString(R.string.clear_cache_success), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClearFailed(Exception e) {
        Logger.wtf(TAG, e);
    }

    @Override
    public void logout() {
        MyAccountManager.removeAccount();
        Toast.makeText(this, AppData.getString(R.string.log_out_success), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void sendEmailIntent(Intent emailIntent) {
        startActivity(Intent.createChooser(emailIntent, AppData.getString(R.string.send_mail)));
    }

    @Override
    public void about() {
        startActivity(new Intent(this, AboutActivity.class));
    }

    @Override
    public void license() {
        startActivity(new Intent(this, LicenseActivity.class));
    }
}
