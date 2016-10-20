package com.hustunique.jianguo.dribile.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;

import butterknife.ButterKnife;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Element versionElement = new Element();
        versionElement.setTitle(AppData.getString(R.string.version));
        Element changeLog = new Element();
        changeLog.setTitle(AppData.getString(R.string.change_log));
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(AppData.getString(R.string.description))
                .setImage(R.mipmap.ic_launcher)
                .addItem(versionElement)
                .addItem(changeLog)
                .addGroup(AppData.getString(R.string.connect))
                .addEmail(AppData.EMAIL)
                .addWebsite(AppData.HOME_PAGE)
                .addTwitter(AppData.TWITTER)
                .addGitHub(AppData.GITHUB)
                .create();
        setContentView(aboutPage);
    }
}
