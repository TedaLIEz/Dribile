package com.hustunique.jianguo.dribile.views;

import com.hustunique.jianguo.dribile.ui.activity.AuthActivity;

/**
 * Created by JianGuo on 5/1/16.
 * View for AuthActivity
 */
public interface AuthView {
    void onError(Throwable e);
    AuthActivity getRef();
    void onSuccess();
}
