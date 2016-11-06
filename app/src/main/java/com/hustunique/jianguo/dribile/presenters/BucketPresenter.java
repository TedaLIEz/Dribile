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

import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.views.BucketView;

/**
 * Created by JianGuo on 5/5/16.
 * Presenter for buckets item.
 */
public class BucketPresenter extends BasePresenter<Buckets, BucketView> {

    @Override
    protected void updateView() {
        view().setCount(model.getShots_count());
        view().setName(model.getName());
    }



    public void onBucketClicked() {
        if (setupDone()) {
            view().goToDetailView(model);
        }
    }

    public boolean onBucketLongClick() {
        if (setupDone()) {
            return view().onLongClick(model);
        }
        return false;
    }
}
