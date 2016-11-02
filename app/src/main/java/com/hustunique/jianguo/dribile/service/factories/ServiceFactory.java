package com.hustunique.jianguo.dribile.service.factories;

import com.hustunique.jianguo.dribile.models.AccessToken;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by JianGuo on 3/30/16.
 * Skeleton class for service factory
 */
abstract class ServiceFactory {
    protected static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    static class MyInterceptor implements Interceptor {

        private AccessToken mToken;
        MyInterceptor(AccessToken token) {
            mToken = token;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", mToken.toString())
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }
}
