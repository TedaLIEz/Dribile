package com.hustunique.jianguo.dribile.presenters;

import com.hustunique.jianguo.dribile.dao.AuthUserDataHelper;
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
            view().onClearFailed();
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
        AuthUserDataHelper authUserDataHelper = new AuthUserDataHelper();
        int row = authUserDataHelper.deleteAll();
        view().logout();
    }

    public void sendEmail() {

    }

    public void about() {

    }
}
