package com.hustunique.jianguo.driclient.views;

import com.hustunique.jianguo.driclient.ui.activity.AuthActivity;

/**
 * Created by JianGuo on 5/1/16.
 */
public interface AuthView {
    void onError(Throwable e);
    AuthActivity getRef();
    void onSuccess();
}
