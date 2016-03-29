package com.hustunique.jianguo.driclient.service;

import com.hustunique.jianguo.driclient.bean.Buckets;
import com.hustunique.jianguo.driclient.bean.Comments;
import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.service.api.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by JianGuo on 3/29/16.
 * Request for dribbble shots
 */
public interface DribbbleShotsService {
    /**
     * Get all the shots
     * @return all shots.
     */
    @GET(Constants.URL_BASE_SHOTS)
    Call<Shots> getAllShots();

    /**
     * Get shot by id
     * @param id the shot id
     * @return the shot
     */
    @GET(Constants.URL_BASE_SHOTS + "{id}")
    Call<Shots> getShotById(@Path("id") String id);

    /**
     * Get buckets which includes the shot
     * @param id the shot id
     * @return buckets including the shot.
     */
    @GET(Constants.URL_BASE_SHOTS + "{id}" + Constants.URL_BASE_BUCKETS)
    Call<Buckets> getBuckets(@Path("id") String id);

    /**
     * Get comments in the shot
     * @param id the shot's id
     * @return comments in this shot.
     */
    @GET(Constants.URL_BASE_SHOTS + "{id}" + Constants.URL_BASE_COMMENTS)
    Call<Comments> getComment(@Path("id") String id);
}
