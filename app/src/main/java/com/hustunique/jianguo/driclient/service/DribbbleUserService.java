package com.hustunique.jianguo.driclient.service;

import com.hustunique.jianguo.driclient.models.Buckets;
import com.hustunique.jianguo.driclient.models.Followee;
import com.hustunique.jianguo.driclient.models.Follower;
import com.hustunique.jianguo.driclient.models.Likes;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.models.User;
import com.hustunique.jianguo.driclient.service.api.Constants;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by JianGuo on 3/29/16.
 * Dribbble user service
 */
public interface DribbbleUserService {

    /**
     * find user by name
     *
     * @param name the user name
     * @return the user
     */
    @GET(Constants.URL_BASE_USERS + "{name}")
    Call<User> getUser(@Path("name") String name);

    @Deprecated
    @GET(Constants.OAuth.URL_AUTH_USER)
    Observable<User> getAuthUser();

    /**
     * Get a user's buckets
     *
     * @param id the user id
     * @return the user's buckets
     */
    @GET(Constants.URL_BASE_USERS + "{id}/" + Constants.URL_BASE_BUCKETS)
    Observable<List<Buckets>> getBuckets(@Path("id") String id);

    /**
     * return the authenticated user’s buckets.
     *
     * @return the authenticated user’s buckets.
     */
    @GET(Constants.OAuth.URL_AUTH_USER + Constants.URL_BASE_BUCKETS)
    Observable<List<Buckets>> getAuthBuckets();

    /**
     * Get followers following the user
     *
     * @param name user name
     * @return followers
     */
    @GET(Constants.URL_BASE_USERS + "{name}/" + Constants.URL_BASE_FOLLOWERS)
    Call<Follower> getFollowers(@Path("name") String name);


    /**
     * Get the auth user's followers
     *
     * @return the auth user's followers
     */
    @Deprecated
    @GET(Constants.OAuth.URL_AUTH_USER + Constants.URL_BASE_FOLLOWERS)
    Call<Follower> getAuthFollowers();


    /**
     * Get followees
     *
     * @param name the user name
     * @return users followed by the user
     */
    @GET(Constants.URL_BASE_USERS + "{name}/" + Constants.URL_BASE_FOLLOWING)
    Call<Followee> getFollowees(@Path("name") String name);

    /**
     * get the user's followees
     *
     * @return the auth user's followees
     */
    @Deprecated
    @GET(Constants.OAuth.URL_AUTH_USER + Constants.URL_BASE_FOLLOWING)
    Call<Followee> getAuthFollowees();


    /**
     * Get shots by user id
     *
     * @param id     the user id
     * @param params params
     * @return list of shots.
     */
    @GET(Constants.URL_BASE_USERS + "{id}/" + Constants.URL_BASE_SHOTS)
    Observable<List<Shots>> getShots(@Path("id") String id, @QueryMap Map<String, String> params);



    @GET(Constants.URL_BASE_USERS + "{id}/" + Constants.URL_BASE_LIKES)
    Observable<List<Likes>> getLikeShots(@Path("id") String id, @QueryMap Map<String, String> params);

    @GET(Constants.OAuth.URL_AUTH_USER + Constants.URL_BASE_LIKES)
    Observable<List<Likes>> getAuthLikeShots(@QueryMap Map<String, String> params);

    @GET(Constants.OAuth.URL_AUTH_USER + Constants.URL_BASE_FOLLOWING + "{userid}/")
    Observable<Response<ResponseBody>> isFollowed(@Path("userid") String userid);

    @PUT(Constants.URL_BASE_USERS + "{userid}/" + Constants.URL_BASE_FOLLOW)
    Observable<Response<ResponseBody>> follow(@Path("userid") String userid);

    @DELETE(Constants.URL_BASE_USERS + "{userid}/" + Constants.URL_BASE_FOLLOW)
    Observable<Response<ResponseBody>> unFollow(@Path("userid") String userid);


}
