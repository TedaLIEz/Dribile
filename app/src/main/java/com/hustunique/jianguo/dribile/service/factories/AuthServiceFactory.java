package com.hustunique.jianguo.dribile.service.factories;

import com.hustunique.jianguo.dribile.service.api.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JianGuo on 3/30/16.
 * Service Factory creating auth service
 */
public class AuthServiceFactory extends ServiceFactory {

    private static Retrofit.Builder authBuilder
            = new Retrofit.Builder()
            .baseUrl(Constants.OAuth.URL_BASE_OAUTH)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    /**
     * Create auth service via {@link com.hustunique.jianguo.dribile.service.api.Constants.OAuth}
     * @param serviceClass the service class
     * @param <S> class name
     * @return the service
     */
    public static <S> S createAuthService(Class<S> serviceClass) {
        Retrofit retrofit = authBuilder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

}
