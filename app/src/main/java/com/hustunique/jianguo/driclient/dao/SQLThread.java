package com.hustunique.jianguo.driclient.dao;

import android.os.HandlerThread;

/**
 * Created by JianGuo on 4/8/16.
 */

//TODO: Save all data in background using this class!!!
public class SqlThread extends HandlerThread {

    public SqlThread(String name, BasicDataHelper dataHelper) {
        super(name);
    }

    public SqlThread(String name, int priority) {
        super(name, priority);
    }



}
