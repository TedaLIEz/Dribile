package com.hustunique.jianguo.driclient.ui.widget;

import android.view.View;

import com.hustunique.jianguo.driclient.app.UserManager;
import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleLikeService;
import com.hustunique.jianguo.driclient.service.factories.ResponseBodyFactory;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by JianGuo on 4/16/16.
 * ClickListener dealing with like
 */
public abstract class ShotLikeClickListener implements View.OnClickListener{
    private boolean isLike = false;
    private DribbbleLikeService dribbbleLikeService;
    private Shots shots;
    public abstract void isLike();
    public abstract void onLike();
    public abstract void onUnlike();
    public abstract void onPreLike();
    public abstract void onPreUnlike();
    public ShotLikeClickListener(Shots shots) {
        this.shots = shots;
        dribbbleLikeService = ResponseBodyFactory.createService(DribbbleLikeService.class);
        dribbbleLikeService.like(shots.getId()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 200) {
                            isLike = true;
                            isLike();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (isLike) {
            onPreUnlike();
            dribbbleLikeService.unlike(shots.getId()).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Response<ResponseBody>>() {
                        @Override
                        public void call(Response<ResponseBody> responseBodyResponse) {
                            if (responseBodyResponse.code() == 204) {
                                onUnlike();
                            }
                        }
                    });
            isLike = false;
        } else {
            onPreLike();
            dribbbleLikeService.like(shots.getId()).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Response<ResponseBody>>() {
                        @Override
                        public void call(Response<ResponseBody> responseBodyResponse) {
                            if (responseBodyResponse.code() == 201) {
                                onLike();
                            }
                        }
                    });
            isLike = true;
        }
    }
}
