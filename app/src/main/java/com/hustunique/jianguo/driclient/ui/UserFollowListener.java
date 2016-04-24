package com.hustunique.jianguo.driclient.ui;

import android.util.Log;
import android.view.View;

import com.hustunique.jianguo.driclient.app.UserManager;
import com.hustunique.jianguo.driclient.bean.User;
import com.hustunique.jianguo.driclient.service.DribbbleUserService;
import com.hustunique.jianguo.driclient.service.factories.ResponseBodyFactory;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by JianGuo on 4/24/16.
 */
public abstract class UserFollowListener implements View.OnClickListener {

    private DribbbleUserService dribbbleUserService;
    private User targetUser;
    private boolean isFollowed = false;
    public abstract void isFollowed();
    public abstract void onFollowing();
    public abstract void onPreFollowing();
    public abstract void onPreUnFollowing();
    public abstract void onUnFollowing();
    public UserFollowListener(User user) {
        dribbbleUserService = ResponseBodyFactory.createService(DribbbleUserService.class);
        targetUser = user;
        dribbbleUserService.isFollowed(user.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 204) {
                            isFollowed();
                            isFollowed = true;
                        } else if (responseBodyResponse.code() == 404) {
                            isFollowed = false;
                        } else {
                            Log.e("driclient", "network error " + responseBodyResponse.code());
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (isFollowed) {
            onPreUnFollowing();
            dribbbleUserService.unFollow(targetUser.getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Action1<Response<ResponseBody>>() {
                        @Override
                        public void call(Response<ResponseBody> responseBodyResponse) {
                            if (responseBodyResponse.code() == 204) {
                                isFollowed = false;
                                onUnFollowing();
                            }
                        }
                    });
        } else {
            onPreFollowing();
            dribbbleUserService.follow(targetUser.getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Action1<Response<ResponseBody>>() {
                        @Override
                        public void call(Response<ResponseBody> responseBodyResponse) {
                            if (responseBodyResponse.code() == 204) {
                                isFollowed = true;
                                onFollowing();
                            }
                        }
                    });
        }
    }
}
