package com.hustunique.jianguo.driclient.service;

import com.hustunique.jianguo.driclient.bean.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by JianGuo on 3/28/16.
 * Dribbble OAuth Service
 */
public interface DribbbleAuthService {
    @GET("users/{username}")
    Call<User> getUser(@Header("Authorization") String authorization, @Path("username") String username);
}
