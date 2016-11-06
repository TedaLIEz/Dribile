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

package com.hustunique.jianguo.dribile.presenters;

import android.content.Intent;

import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.views.LoginView;

/**
 * Created by JianGuo on 5/1/16.
 * Presenter for LoginActivity
 */
public class LoginPresenter extends BasePresenter<Boolean, LoginView> {
    private static final String TAG = "LoginPresenter";
    public static int LOGIN = 0x00000000;

    @Override
    protected void updateView() {
        view().goToMain();
    }

    public void login(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN) {
            switch (resultCode) {
                case AuthPresenter.AUTH_DENIED:
                    Logger.e(TAG," user deny the authentications");
                    break;
                case AuthPresenter.AUTH_FAILED:
                    String msg = data.getStringExtra(AuthPresenter.ERR_AUTH_MSG);
                    Logger.e(TAG, " login failed " + msg);
                    break;
                case AuthPresenter.AUTH_CANCELED:
                    break;
                case AuthPresenter.AUTH_OK:
                    setModel(true);
                    break;
                default:
                    break;
            }
        }
    }


    public void auth() {
        view().goToAuth(LOGIN);
    }


}
