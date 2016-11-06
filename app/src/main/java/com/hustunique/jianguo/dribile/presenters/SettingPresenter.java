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
import android.net.Uri;

import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.dao.LikesDataHelper;
import com.hustunique.jianguo.dribile.dao.ShotsDataHelper;
import com.hustunique.jianguo.dribile.views.SettingView;

/**
 * Created by JianGuo on 5/26/16.
 * Presenter for setting activity.
 */
public class SettingPresenter extends BasePresenter<Void, SettingView>{

    @Override
    protected void updateView() {

    }

    public void clear() {
        try {
            clearData();
            view().onClearSuccess();
        } catch (Exception e) {
            view().onClearFailed(e);
        }
    }

    private void clearData() {
        ShotsDataHelper shotsDataHelper = new ShotsDataHelper();
        shotsDataHelper.deleteAll();
        LikesDataHelper likesDataHelper = new LikesDataHelper();
        likesDataHelper.deleteAll();
    }

    public void logout() {
        clearData();
        view().logout();
    }

    public void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {AppData.EMAIL} );
        view().sendEmailIntent(emailIntent);
    }

    public void about() {
        view().about();
    }

    public void license() {
        view().license();
    }
}
