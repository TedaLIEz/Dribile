package com.hustunique.jianguo.driclient.service.factories;

import okhttp3.OkHttpClient;

/**
 * Created by JianGuo on 3/30/16.
 * Skeleton class for service factory
 */
public abstract class ServiceFactory {
    protected static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
}
