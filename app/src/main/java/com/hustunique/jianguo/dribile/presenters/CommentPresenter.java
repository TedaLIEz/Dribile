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
import com.hustunique.jianguo.dribile.utils.Utils;
import com.hustunique.jianguo.dribile.views.CommentView;

/**
 * Created by JianGuo on 5/4/16.
 * Presenter for each comments item
 */
public class CommentPresenter extends BasePresenter<Comments, CommentView> {
    @Override
    protected void updateView() {
        view().setAvatar(model.getUser().getAvatar_url());
        view().setBody(model.getBody());
        view().setName(model.getUser().getName());
        view().setTime(Utils.currentTimeLine(model.getUpdated_at()));
        view().setLikeCount(model.getLikes_count());
    }

    public void onCommentClicked() {
        if (setupDone()) {
            view().goToDetailView(model);
        }
    }


}
