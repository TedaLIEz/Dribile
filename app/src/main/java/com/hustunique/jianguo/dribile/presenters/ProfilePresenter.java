package com.hustunique.jianguo.dribile.presenters;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.hustunique.jianguo.dribile.am.MyAccountManager;
import com.hustunique.jianguo.dribile.models.User;
import com.hustunique.jianguo.dribile.presenters.strategy.GetUserByIdStrategy;
import com.hustunique.jianguo.dribile.presenters.strategy.ILoadDataStrategy;
import com.hustunique.jianguo.dribile.service.DribbbleUserService;
import com.hustunique.jianguo.dribile.service.factories.ResponseBodyFactory;
import com.hustunique.jianguo.dribile.utils.Utils;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.utils.NetUtils;
import com.hustunique.jianguo.dribile.utils.ObservableTransformer;
import com.hustunique.jianguo.dribile.views.ProfileView;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.functions.Action1;

/**
 * Created by JianGuo on 5/8/16.
 */
public class ProfilePresenter extends BasePresenter<User, ProfileView> {
    private static final String TAG = "ProfilePresenter";
    private ILoadDataStrategy<User> strategy;
    private boolean isLoaded;
    public ProfilePresenter(String id) {

        strategy = new GetUserByIdStrategy(id);
        strategy.loadData(null)
                .compose(new ObservableTransformer<User>())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        setModel(user);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view().onError(throwable);
                    }
                });
    }

    public ProfilePresenter(User user) {
        setModel(user);
    }


    private boolean isFollowed = false;

    @Override
    protected void updateView() {
        isLoaded = true;
        view().showData();
        view().setBio(model.getBio());
        view().setName(model.getName());

        view().setFollowerCount(Utils.numToK(model.getFollowers_count()));
        view().setFollowingCount(Utils.numToK(model.getFollowings_count()));
        view().setLikeCount(Utils.numToK(model.getLikes_count()));
        view().setLocation(Utils.numToK(model.getLocation()));
        view().setShotCount(Utils.numToK(model.getShots_count()));
        ResponseBodyFactory.createService(DribbbleUserService.class)
                .isFollowed(model.getId())
                .compose(new ObservableTransformer<Response<ResponseBody>>())
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
                            Logger.e(TAG, "network error " + responseBodyResponse.code());
                        }
                    }
                });
        view().initFollow(model.equals(MyAccountManager.getCurrentUser().getUser()));
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

    @Override
    public void bindView(@NonNull ProfileView view) {
        super.bindView(view);
        if (!isLoaded) {
            view().showLoading();
        }

    }

    public void onFollowClick() {
        if (isFollowed) {
            ResponseBodyFactory.createService(DribbbleUserService.class)
                    .unFollow(model.getId())
                    .compose(new ObservableTransformer<Response<ResponseBody>>())
                    .subscribe(new Action1<Response<ResponseBody>>() {
                        @Override
                        public void call(Response<ResponseBody> responseBodyResponse) {
                            if (responseBodyResponse.code() == 204) {
                                isFollowed = false;
                                view().unfollowed();
                                MyAccountManager.updateUser();
                            }
                        }
                    });
        } else {
            ResponseBodyFactory.createService(DribbbleUserService.class)
                    .follow(model.getId())
                    .compose(new ObservableTransformer<Response<ResponseBody>>())
                    .subscribe(new Action1<Response<ResponseBody>>() {
                        @Override
                        public void call(Response<ResponseBody> responseBodyResponse) {
                            if (responseBodyResponse.code() == 204) {
                                isFollowed = true;
                                view().followed();
                                MyAccountManager.updateUser();
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
