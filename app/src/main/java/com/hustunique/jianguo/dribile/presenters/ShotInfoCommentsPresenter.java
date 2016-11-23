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

import com.hustunique.jianguo.dribile.models.Comments;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.presenters.strategy.GetCommentsByIdStrategy;
import com.hustunique.jianguo.dribile.service.endpoint.DribbbleShotsService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.utils.ObservableTransformer;
import com.hustunique.jianguo.dribile.views.ShotInfoCommentView;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by JianGuo on 5/7/16.
 * Presenter for comments in ShotInfoActivity
 */
public class ShotInfoCommentsPresenter extends BaseListPresenter<Comments, ShotInfoCommentView> {
    private Shots mShot;
    private static final int COMMENTS_PER_PAGE = 5;
    private static final String TAG = "ShotInfoCommentsPresenter";
    public ShotInfoCommentsPresenter(Shots shots) {
        super();
        this.mShot = shots;
        mLoadDel.setLoadStrategy(new GetCommentsByIdStrategy(shots.getId()));
        mLoadDel.setPerPage(COMMENTS_PER_PAGE);
    }

    public ShotInfoCommentsPresenter(String id) {
        super();
        ApiServiceFactory.createService(DribbbleShotsService.class)
                .getShotById(id)
                .compose(new ObservableTransformer<Shots>())
                .subscribe(new Action1<Shots>() {
                    @Override
                    public void call(Shots shots) {
                        ShotInfoCommentsPresenter.this.mShot = shots;

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.wtf(TAG, throwable);
                    }
                });
        mLoadDel.setLoadStrategy(new GetCommentsByIdStrategy(id));
        mLoadDel.setPerPage(COMMENTS_PER_PAGE);
    }

    @Override
    protected void updateView() {
        if (Integer.parseInt(mShot.getComments_count()) >= COMMENTS_PER_PAGE) {
            view().showLoadingMore();
        }
        if (model.size() == 0) view().showEmpty();
        else view().showData(model);

    }

    @Override
    public void getData() {
        refresh();
    }

    @Override
    public void refresh() {
        mLoadDel.loadData()
                .compose(new ObservableTransformer<List<Comments>>())
                .subscribe(new LoadingListSubscriber() {
            @Override
            public void onNext(List<Comments> commentses) {
                setModel(commentses);
            }
        });
    }


    public void goToMoreComments() {
        view().goToMoreComments(mShot);
    }


}
