package com.hustunique.jianguo.driclient.service.factories;

import com.hustunique.jianguo.driclient.service.api.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JianGuo on 3/30/16.
 */
public class AuthServiceFactory extends ServiceFactory{
    private static Retrofit.Builder authBuilder
            = new Retrofit.Builder()
            .baseUrl(Constants.OAuth.URL_BASE_OAUTH)
            .addConverterFactory(GsonConverterFactory.create());


    /**
     *
     * @param serviceClass
     * @param <S>
     * @return
     */
    public static <S> S createAuthService(Class<S> serviceClass) {
        Retrofit retrofit = authBuilder.build();
        return retrofit.create(serviceClass);
    }

}
