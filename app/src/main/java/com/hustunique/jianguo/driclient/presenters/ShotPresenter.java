package com.hustunique.jianguo.driclient.presenters;

import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.views.ShotView;

/**
 * Created by JianGuo on 5/3/16.
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
            //TODO: load user avatar.
            view().setAvatar(model.getUser().getAvatar_url());
        } else {
            view().hideAvatar();
        }
    }


    public void onShotClicked() {
        if (setupDone()) {
            view().goToDetailView(model);
        }
    }



}
