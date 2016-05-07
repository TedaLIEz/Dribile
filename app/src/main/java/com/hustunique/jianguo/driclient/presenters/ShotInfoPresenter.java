package com.hustunique.jianguo.driclient.presenters;

import android.content.Intent;
import android.net.Uri;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.app.UserManager;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleLikeService;
import com.hustunique.jianguo.driclient.service.factories.ResponseBodyFactory;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.hustunique.jianguo.driclient.views.ShotInfoView;

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
        view().setShotImage(Uri.parse(model.getImages().getNormal()));
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
                .like(model.getId()).subscribeOn(Schedulers.newThread())
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
        ResponseBodyFactory.createService(DribbbleLikeService.class)
                .unlike(model.getId()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 204) {
                            view().onUnlike();
                            UserManager.updateUser();
                        }
                    }
                });
        isLike = false;
    }

    private void like() {
        ResponseBodyFactory.createService(DribbbleLikeService.class).like(model.getId()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 201) {
                            view().onLike();
                            UserManager.updateUser();
                        }
                    }
                });
        isLike = true;
    }


    public void sendShared() {
        StringBuffer sb = new StringBuffer();
        sb.append(model.getTitle()).append(" - ");
        sb.append(model.getHtml_url()).append("\n");
        sb.append("- Shared from Driclient");
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
