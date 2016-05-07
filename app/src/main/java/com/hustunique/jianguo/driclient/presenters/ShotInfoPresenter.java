package com.hustunique.jianguo.driclient.presenters;

import android.net.Uri;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.hustunique.jianguo.driclient.views.ShotInfoView;

/**
 * Created by JianGuo on 5/6/16.
 */
public class ShotInfoPresenter extends BasePresenter<Shots, ShotInfoView> {

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


}
