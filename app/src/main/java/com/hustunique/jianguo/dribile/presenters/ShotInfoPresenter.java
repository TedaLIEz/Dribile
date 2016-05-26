package com.hustunique.jianguo.dribile.presenters;

import android.content.Intent;
import android.net.Uri;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.app.UserManager;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.service.DribbbleLikeService;
import com.hustunique.jianguo.dribile.service.DribbbleShotsService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.dribile.service.factories.ResponseBodyFactory;
import com.hustunique.jianguo.dribile.utils.CommonUtils;
import com.hustunique.jianguo.dribile.views.ShotInfoView;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by JianGuo on 5/6/16.
 * Presenter for shots in ShotInfoActivity
 */
public class ShotInfoPresenter extends BasePresenter<Shots, ShotInfoView> {
    private boolean isLike = false;
    @Override
    protected void updateView() {
        view().setCommentCount(String.format(AppData.getString(R.string.comments), model.getComments_count()));
        view().setShotImage(model.getImages().getNormal());
        view().setAnimated(model.getAnimated().equals("true"));
        view().setViewCount(String.format(AppData.getString(R.string.views), model.getViews_count()));
        view().setLikeCount(String.format(AppData.getString(R.string.likes), model.getLikes_count()));
        view().setBucketCount(String.format(AppData.getString(R.string.buckets), model.getBuckets_count()));
        view().setDescription(model.getDescription() == null ? AppData.getString(R.string.no_description) : model.getDescription().equals("") ? AppData.getString(R.string.no_description) : model.getDescription());
        view().setTime(String.format(AppData.getString(R.string.shots_time), CommonUtils.formatDate(model.getCreated_at())));
        view().setShotTitle(model.getTitle());
        view().setUserName(model.getUser().getName());
        if (model.getUser() != null) {
            view().setAvatar(Uri.parse(model.getUser().getAvatar_url()));
        } else {
            view().setDefaultAvatar();
        }
        view().setTags(model.getTags());
        ResponseBodyFactory.createService(DribbbleLikeService.class)
                .isLike(model.getId()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 200) {
                            view().onLike();
                            isLike = true;
                        }
                    }
                });
    }

    public ShotInfoPresenter(Shots shots) {
        setModel(shots);
    }

    public ShotInfoPresenter(String id) {
        ApiServiceFactory.createService(DribbbleShotsService.class)
                .getShotById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<Shots>() {
                    @Override
                    public void call(Shots shots) {
                        setModel(shots);
                    }
                });
    }

    public void goToUser() {
        view().goToProfile(model.getUser());
    }

    public void goToDetailView() {
        view().goToDetailView(model);
    }


    public void addToBucket() {
        view().addToBucket(model);
    }

    public void onClick() {
        if (isLike) {
            unlike();
        } else {
            like();
        }
    }

    private void unlike() {
        view().onUnlike();
        ResponseBodyFactory.createService(DribbbleLikeService.class)
                .unlike(model.getId()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 204) {
                            isLike = false;
                            UserManager.updateUser();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view().onLike();
                    }
                });

    }

    private void like() {
        view().onLike();
        ResponseBodyFactory.createService(DribbbleLikeService.class).like(model.getId()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 201) {
                            isLike = true;
                            UserManager.updateUser();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view().onUnlike();
                    }
                });

    }


    public void sendShared() {
        StringBuffer sb = new StringBuffer();
        sb.append(model.getTitle()).append(" - ");
        sb.append(model.getHtml_url()).append("\n");
        sb.append("- Shared from Dribile");
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
        sendIntent.setType("text/plain");
        view().sendSharedIntent(sendIntent);
    }

    public void addComments() {
        view().addComments(model);
    }
}
