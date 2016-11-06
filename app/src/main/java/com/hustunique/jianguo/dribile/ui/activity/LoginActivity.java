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
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.PresenterManager;
import com.hustunique.jianguo.dribile.presenters.LoginPresenter;
import com.hustunique.jianguo.dribile.views.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Login Activity for application
 */
public class LoginActivity extends BaseActivity implements LoginView {
    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 250;

    private boolean animationStarted = false;

    private LoginPresenter mLoginPresenter;
    @BindView(R.id.btn_login)
    Button login;


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus || animationStarted) {
            return;
        }

        animate();

        super.onWindowFocusChanged(hasFocus);
    }

    private void animate() {
        ImageView logoImageView = (ImageView) findViewById(R.id.img_logo);
        ViewGroup container = (ViewGroup) findViewById(R.id.container);

        ViewCompat.animate(logoImageView)
                .translationY(-250)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();

        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            if (!(v instanceof Button)) {
                viewAnimator = ViewCompat.animate(v)
                        .translationY(50).alpha(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(1000);
            } else {
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(500);
            }

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_DesignDemo);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mLoginPresenter = new LoginPresenter();
        } else {
            mLoginPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    void onClick() {
        mLoginPresenter.auth();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginPresenter.login(requestCode, resultCode, data);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mLoginPresenter.bindView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLoginPresenter.unbindView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mLoginPresenter, outState);
    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToAuth(int requestCode) {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivityForResult(intent, requestCode);
    }
}
