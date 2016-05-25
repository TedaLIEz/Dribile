package com.hustunique.jianguo.dribile.presenters;

import android.net.Uri;

import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.views.ShotView;

/**
 * Created by JianGuo on 5/3/16.
 * Presenter of shot item in adapter
 */
public class ShotPresenter extends BasePresenter<Shots, ShotView> {

    @Override
    protected void updateView() {
        view().setAnimated(model.getAnimated().equals("true"));
        view().setCommentCount(model.getComments_count());
        view().setLikeCount(model.getLikes_count());
        view().setShotTitle(model.getTitle());
        view().setViewCount(model.getViews_count());
        view().setShotImage(model.getImages().getNormal());
        if (model.getUser() != null) {
            view().setAvatar(Uri.parse(model.getUser().getAvatar_url()));
        } else {
            view().setDefaultAvatar();
        }
    }


    public void onShotClicked() {
        if (setupDone()) {
            view().goToDetailView(model);
        }
    }



}
