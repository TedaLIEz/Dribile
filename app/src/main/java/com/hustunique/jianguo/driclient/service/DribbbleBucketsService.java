package com.hustunique.jianguo.driclient.service;

import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.service.api.Constants;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by JianGuo on 3/29/16.
 * Dribbble buckets service
 */
public interface DribbbleBucketsService {
    /**
     * Get shots for a buckets
     * @param id the bucket id
     * @return the shot list
     */
    @GET(Constants.URL_BASE_BUCKETS + "{id}/" + Constants.URL_BASE_SHOTS)
    Call<Shots> getShotsFromBuckets(@Path("id") String id);

    /**
     * put a shot into bucket
     * @param id the shot id
     * @return the response
     */
    @PUT(Constants.URL_BASE_BUCKETS + "{id}/" + Constants.URL_BASE_SHOTS)
    Call<Response> putShotsinBucket(@Path("id") String id);

}
