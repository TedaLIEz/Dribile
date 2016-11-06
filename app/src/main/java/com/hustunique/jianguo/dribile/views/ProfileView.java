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

import android.content.Intent;

import com.hustunique.jianguo.dribile.models.User;

/**
 * Created by JianGuo on 5/8/16.
 */
public interface ProfileView {
    void setName(String name);
    void setLocation(String location);
    void setBio(String bio);
    void setFollowerCount(String followerCount);
    void setFollowingCount(String followingCount);
    void setShotCount(String shotCount);
    void setLikeCount(String likeCount);
    void goToTwitter(String twitterUri);
    void goToDribbble(Intent intent);
    void followed();
    void unfollowed();
    void noTwitter();
    void setAvatar(String uri);
    void initFollow(boolean self);

    void goToShotList(User model);

    void goToLikeList(User model);

    void onError(Throwable throwable);

    void showLoading();

    void showData();
}
