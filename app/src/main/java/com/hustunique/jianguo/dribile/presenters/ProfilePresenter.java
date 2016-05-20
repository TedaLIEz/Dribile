package com.hustunique.jianguo.dribile.presenters;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.hustunique.jianguo.dribile.app.UserManager;
import com.hustunique.jianguo.dribile.models.User;
import com.hustunique.jianguo.dribile.presenters.strategy.GetUserByIdStrategy;
import com.hustunique.jianguo.dribile.presenters.strategy.ILoadDataStrategy;
import com.hustunique.jianguo.dribile.service.DribbbleUserService;
import com.hustunique.jianguo.dribile.service.factories.ResponseBodyFactory;
import com.hustunique.jianguo.dribile.utils.NetUtils;
import com.hustunique.jianguo.dribile.views.ProfileView;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by JianGuo on 5/8/16.
 */
public class ProfilePresenter extends BasePresenter<User, ProfileView> {
    private ILoadDataStrategy<User> strategy;
    public ProfilePresenter(String id) {
        strategy = new GetUserByIdStrategy(id);
        strategy.loadData(null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        setModel(user);
                    }
                });
    }

    public ProfilePresenter(User user) {
        setModel(user);
    }


    private boolean isFollowed = false;

    @Override
    protected void updateView() {
        view().setBio(model.getBio());
        view().setName(model.getName());
        view().setFollowerCount(model.getFollowers_count());
        view().setFollowingCount(model.getFollowings_count());
        view().setLikeCount(model.getLikes_count());
        view().setLocation(model.getLocation());
        view().setShotCount(model.getShots_count());
        ResponseBodyFactory.createService(DribbbleUserService.class)
                .isFollowed(model.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 204) {
                            view().followed();
                            isFollowed = true;
                        } else if (responseBodyResponse.code() == 404) {
                            isFollowed = false;
                            view().unfollowed();
                        } else {
                            Log.e("driclient", "network error " + responseBodyResponse.code());
                        }
                    }
                });
        view().initFollow(model.equals(UserManager.getCurrentUser().getUser()));
        view().setAvatar(model.getAvatar_url());
    }

    public void goToTwitter() {
        if (model.getLink() != null && model.getLink().getTwitter() != null) {
            view().goToTwitter(NetUtils.getNameFromTwitterUrl(model.getLink().getTwitter()));
        } else {
            view().noTwitter();
        }
    }

    public void goToDribbble() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getHtml_url()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        view().goToDribbble(intent);
    }

    public void onFollowClick() {
        if (isFollowed) {
            ResponseBodyFactory.createService(DribbbleUserService.class)
                    .unFollow(model.getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Action1<Response<ResponseBody>>() {
                        @Override
                        public void call(Response<ResponseBody> responseBodyResponse) {
                            if (responseBodyResponse.code() == 204) {
                                isFollowed = false;
                                view().unfollowed();
                                UserManager.updateUser();
                            }
                        }
                    });
        } else {
            ResponseBodyFactory.createService(DribbbleUserService.class)
                    .follow(model.getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Action1<Response<ResponseBody>>() {
                        @Override
                        public void call(Response<ResponseBody> responseBodyResponse) {
                            if (responseBodyResponse.code() == 204) {
                                isFollowed = true;
                                view().followed();
                                UserManager.updateUser();
                            }
                        }
                    });
        }
    }

    public void goToShotList() {
        if (!model.getShots_count().equals("0")) {
            view().goToShotList(model);
        }
    }

    public void goToLikeList() {
        if (!model.getLikes_count().equals("0")) {
            view().goToLikeList(model);
        }
    }
}
