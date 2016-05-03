package com.hustunique.jianguo.driclient.presenters;

import android.content.Context;

import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.views.ShotView;

/**
 * Created by JianGuo on 5/3/16.
 */
public class ShotPresenter extends BasePresenter<Shots, ShotView> {

    @Override
    protected void updateView() {
        view().setAnimated(model.getAnimated());
        view().setCommentCount(model.getComments_count());
        view().setLikeCount(model.getLikes_count());
        view().setShotTitle(model.getTitle());
        view().setViewCount(model.getViews_count());
    }

    public void loadImage(Context context) {
    }

}
