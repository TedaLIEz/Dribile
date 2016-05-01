package com.hustunique.jianguo.driclient.presenters;

import com.hustunique.jianguo.driclient.models.User;
import com.hustunique.jianguo.driclient.views.AuthView;

/**
 * Created by JianGuo on 5/1/16.
 */
public class AuthPresenter extends BasePresenter<User, AuthView> {
    public static final int AUTH_DENIED = 0x11111111;
    public static final int AUTH_FAILED = 0x11111110;
    public static final int AUTH_CANCELED = 0x11111100;
    public static final int AUTH_OK = 0x11111101;
    @Override
    protected void updateView() {

    }
}
