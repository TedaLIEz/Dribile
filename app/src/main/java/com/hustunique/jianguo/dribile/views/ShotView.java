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

import com.hustunique.jianguo.dribile.models.Shots;

/**
 * Created by JianGuo on 5/3/16.
 */
public interface ShotView {
    void setShotTitle(String title);
    void setViewCount(String viewCount);
    void setCommentCount(String commentCount);
    void setLikeCount(String likeCount);
    void setAnimated(boolean animated);
    void setShotImage(String imageUrl);
    void setAvatar(Uri avatar_url);
    void goToDetailView(Shots model);
    void setDefaultAvatar();


}
