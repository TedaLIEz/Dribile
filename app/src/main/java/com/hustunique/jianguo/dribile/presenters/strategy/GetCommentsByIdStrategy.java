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

import com.hustunique.jianguo.dribile.models.Comments;
import com.hustunique.jianguo.dribile.service.DribbbleShotsService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 5/6/16.
 * Loading comments by shot's id.
 */
public class GetCommentsByIdStrategy implements ILoadListDataStrategy<Comments> {
    private final String id;

    private Map<String, String> params;
    public GetCommentsByIdStrategy(String id) {
        this.id = id;
    }



    @Override
    public Observable<List<Comments>> loadData(Map<String, String> params) {
        return ApiServiceFactory.createService(DribbbleShotsService.class)
                .getComment(id, params);
    }
}
