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

import android.content.Intent;
import android.net.Uri;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.am.MyAccountManager;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.service.endpoint.DribbbleLikeService;
import com.hustunique.jianguo.dribile.service.endpoint.DribbbleShotsService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.dribile.service.factories.ResponseBodyFactory;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.utils.Utils;
import com.hustunique.jianguo.dribile.utils.ObservableTransformer;
import com.hustunique.jianguo.dribile.views.ShotInfoView;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.functions.Action1;

/**
 * Created by JianGuo on 5/6/16.
 * Presenter for shots in ShotInfoActivity
 */
public class ShotInfoPresenter extends BasePresenter<Shots, ShotInfoView> {
    private boolean isLike = false;
    private static final String TAG = "ShotInfoPresenter";
    @Override
    protected void updateView() {
        view().setCommentCount(String.format(AppData.getString(R.string.comments), model.getComments_count()));
        view().setShotImage(model.getImages().getNormal());
        view().setAnimated(model.getAnimated().equals("true"));
        view().setViewCount(String.format(AppData.getString(R.string.views), model.getViews_count()));
        view().setLikeCount(String.format(AppData.getString(R.string.likes), model.getLikes_count()));
        view().setBucketCount(String.format(AppData.getString(R.string.buckets), model.getBuckets_count()));
        view().setDescription(model.getDescription() == null ? AppData.getString(R.string.no_description) : model.getDescription().equals("") ? AppData.getString(R.string.no_description) : model.getDescription());
        view().setTime(String.format(AppData.getString(R.string.shots_time), Utils.formatDate(model.getCreated_at())));
        view().setShotTitle(model.getTitle());
        view().setUserName(model.getUser().getName());
        if (model.getUser() != null) {
            view().setAvatar(Uri.parse(model.getUser().getAvatar_url()));
        } else {
            view().setDefaultAvatar();
        }
        view().setTags(model.getTags());
        ResponseBodyFactory.createService(DribbbleLikeService.class)
                .isLike(model.getId())
                .compose(new ObservableTransformer<Response<ResponseBody>>())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 200) {
                            view().onLike();
                            isLike = true;
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.wtf(TAG, throwable);
                    }
                });
    }

    public ShotInfoPresenter(Shots shots) {
        setModel(shots);
    }

    public ShotInfoPresenter(String id) {
        ApiServiceFactory.createService(DribbbleShotsService.class)
                .getShotById(id)
                .compose(new ObservableTransformer<Shots>())
                .subscribe(new Action1<Shots>() {
                    @Override
                    public void call(Shots shots) {
                        setModel(shots);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.wtf(TAG, throwable);
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
                .unlike(model.getId())
                .compose(new ObservableTransformer<Response<ResponseBody>>())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 204) {
                            isLike = false;
                            MyAccountManager.updateUser();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.wtf(TAG, throwable);
                        view().onLike();
                    }
                });

    }

    private void like() {
        view().onLike();
        ResponseBodyFactory.createService(DribbbleLikeService.class).like(model.getId())
                .compose(new ObservableTransformer<Response<ResponseBody>>())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 201) {
                            isLike = true;
                            MyAccountManager.updateUser();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.wtf(TAG, throwable);
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
