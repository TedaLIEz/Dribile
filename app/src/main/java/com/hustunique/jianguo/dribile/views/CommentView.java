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

import com.hustunique.jianguo.dribile.models.Comments;

/**
 * Created by JianGuo on 5/4/16.
 * View for each comments item
 */
public interface CommentView {
    void setName(String name);
    void setBody(String body);
    void setTime(String time);
    void setLikeCount(String likeCount);
    void setAvatar(String avatar);

    void goToDetailView(Comments model);
}
