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

import android.support.annotation.NonNull;

import com.hustunique.jianguo.dribile.presenters.strategy.ILoadListDataStrategy;
import com.hustunique.jianguo.dribile.presenters.strategy.LoadDataDelegate;
import com.hustunique.jianguo.dribile.views.ILoadListView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by JianGuo on 5/4/16.
 * Base Presenter for loading list of data M
 */
public abstract class BaseListPresenter<M, V extends ILoadListView<M>> extends BasePresenter<List<M>, V> {
    protected LoadDataDelegate<M> mLoadDel;

    protected boolean isLoadingData = false;

    public BaseListPresenter() {
        mLoadDel = new LoadDataDelegate<>();
    }

    public void setLoadStrategy(ILoadListDataStrategy<M> loadStrategy) {
        mLoadDel.setLoadStrategy(loadStrategy);
    }

    @Override
    protected void updateView() {
        if (model.size() == 0) view().showEmpty();
        else view().showData(model);
    }

    @Override
    public void bindView(@NonNull V view) {
        super.bindView(view);
        if (model == null && !isLoadingData) {
            view.showLoading();
            getData();
        }
    }

    public abstract void getData();
    public abstract void refresh();


    @Override
    protected void resetState() {
        super.resetState();
        isLoadingData = !isLoadingData;
    }


    public abstract class LoadingListSubscriber extends Subscriber<List<M>> {

        @Override
        public void onCompleted() {
            isLoadingData = false;
        }

        @Override
        public void onError(Throwable e) {
            if (view() != null) {
                view().onError(e);
            }
        }
    }

}
