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

package com.hustunique.jianguo.dribile.presenters.strategy;

import com.hustunique.jianguo.dribile.models.User;
import com.hustunique.jianguo.dribile.service.endpoint.DribbbleUserService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;

import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 5/14/16.
 * Strategy for loading a user by user's id
 */
public class GetUserByIdStrategy implements ILoadDataStrategy<User> {
    private final String id;

    public GetUserByIdStrategy(String id) {
        this.id = id;
    }

    @Override
    public Observable<User> loadData(Map<String, String> params) {
        return ApiServiceFactory.createService(DribbbleUserService.class).getUser(id);
    }
}
