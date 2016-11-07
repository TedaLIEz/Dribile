/*
 * Copyright (c) 2016.  TedaLIEz <aliezted@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hustunique.jianguo.dribile.presenters;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.views.ShotSearchView;

/**
 * Created by JianGuo on 5/3/16.
 * Presenter of shot item in adapter
 */
public class ShotPresenter extends BasePresenter<Shots, ShotSearchView> {
    private String keywords;
    @Override
    protected void updateView() {
        view().setAnimated(model.getAnimated().equals("true"));
        view().setCommentCount(model.getComments_count());
        view().setLikeCount(model.getLikes_count());

        view().setShotTitle(model.getTitle(), keywords);
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


    public void setHighLight(@Nullable String searchKeyWords) {
        keywords = searchKeyWords;
    }
}
