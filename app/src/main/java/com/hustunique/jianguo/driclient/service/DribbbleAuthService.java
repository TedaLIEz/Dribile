package com.hustunique.jianguo.driclient.service;

import com.hustunique.jianguo.driclient.bean.AccessToken;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by JianGuo on 3/28/16.
 * Dribbble OAuth Service
 */
public interface DribbbleAuthService {

    /**
     * Get token
     * @param clientId the client_id
     * @param secret the client_secret
     * @param code the code
     * @return the AccessToken
     */
    @FormUrlEncoded
    @POST("token")
    Observable<AccessToken> getAccessToken(@Field("client_id") String clientId,
                                           @Field("client_secret") String secret,
                                           @Field("code") String code);
}
