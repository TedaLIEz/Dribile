package com.hustunique.jianguo.driclient.service.factories;

import com.hustunique.jianguo.driclient.bean.AccessToken;
import com.hustunique.jianguo.driclient.service.api.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * Created by JianGuo on 4/13/16.
 * Factory for creating simple service without java bean.
 */
public class ResponseBodyFactory extends ServiceFactory {
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.URL_BASE)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());


    public static <S> S createService(Class<S> serviceClass, final AccessToken token) {
        if (token != null) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", token.toString())
                            .method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
