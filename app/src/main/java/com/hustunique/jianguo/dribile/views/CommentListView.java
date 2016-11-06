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

package com.hustunique.jianguo.dribile.views;

import android.net.Uri;

import com.hustunique.jianguo.dribile.models.Comments;

/**
 * Created by JianGuo on 5/6/16.
 * View for Comments ListView
 */
public interface CommentListView extends ILoadListView<Comments> {
    void onAddCommentSuccess(Comments comment);
    void onAddCommentError(Throwable e);
    void setTitle(String title);
    void setSubTitle(String subTitle);
    void setAvatar(Uri avatar_url);
}
