package com.hustunique.jianguo.dribile.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.hustunique.jianguo.dribile.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Element versionElement = new Element();
        versionElement.setTitle("Version 0.1");
        Element changeLog = new Element();
        changeLog.setTitle("Changelog");
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription("Simple Application using dribbble api")
                .setImage(R.mipmap.ic_launcher)
                .addItem(versionElement)
                .addItem(changeLog)
                .addGroup("Connect to us")
                .addEmail("aliezted@gmail.com")
                .addWebsite("http://tedaliez.github.io")
                .addTwitter("TedaLIEz")
                .addGitHub("TedaLIEz/dribile")
                .create();
        setContentView(aboutPage);
    }
}
