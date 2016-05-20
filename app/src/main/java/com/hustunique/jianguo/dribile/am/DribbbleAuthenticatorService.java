package com.hustunique.jianguo.dribile.am;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by JianGuo on 4/2/16.
 * Service for Dribbble authenticator
 */
public class DribbbleAuthenticatorService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new DribbbleAuthenticator(this).getIBinder();
    }
}
