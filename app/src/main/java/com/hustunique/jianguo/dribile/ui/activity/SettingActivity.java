/*
 * Copyright (c) 2016.  TedaLIEz <aliezted@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements SettingView {

    private static final int SETTING = 0x000000;
    private static final int CLEAR_DATA = 0x000001;
    private static final String TAG = "SettingActivity";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.layout_clear)
    LinearLayout mClean;
    @BindView(R.id.layout_contact)
    LinearLayout mContact;

    @BindView(R.id.layout_about)
    LinearLayout mAbout;

    @BindView(R.id.layout_logout)
    LinearLayout mLogout;
    @BindView(R.id.layout_license)
    LinearLayout mLicense;

    @BindView(R.id.rootView)
    CoordinatorLayout rootView;

    private SettingPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
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
        setResult(CLEAR_DATA);
        Toast.makeText(this, R.string.clear_cache_success, Toast.LENGTH_SHORT).show();
        finish();
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
