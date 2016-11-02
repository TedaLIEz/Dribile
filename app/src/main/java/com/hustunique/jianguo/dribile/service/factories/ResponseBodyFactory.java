package com.hustunique.jianguo.dribile.service.factories;

import com.hustunique.jianguo.dribile.am.MyAccountManager;
import com.hustunique.jianguo.dribile.models.AccessToken;
import com.hustunique.jianguo.dribile.service.api.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by JianGuo on 4/13/16.
 * Factory for creating simple service without java bean.
 */
public class ResponseBodyFactory extends ServiceFactory {
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.URL_BASE)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());


    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, MyAccountManager.getCurrentToken());
    }

    public static <S> S createService(Class<S> serviceClass, AccessToken token) {
        if (token != null) {
            httpClient.addInterceptor(new MyInterceptor(token));
        }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
