package com.hustunique.jianguo.dribile.presenters;

import android.content.Intent;
import android.net.Uri;

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
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"aliezted@gmail.com"} );
        view().sendEmailIntent(emailIntent);
    }

    public void about() {
        view().about();
    }

    public void license() {
        view().license();
    }
}
