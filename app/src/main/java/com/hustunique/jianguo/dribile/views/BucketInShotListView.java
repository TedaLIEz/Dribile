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

import com.hustunique.jianguo.dribile.models.Buckets;

/**
 * Created by JianGuo on 5/3/16.
 * View for BucketInShotActivity
 */
public interface BucketInShotListView extends ILoadListView<Buckets> {

    void removeBucket(Buckets bucket);

    void createBucket(Buckets bucket);

    void addToBucket(Buckets bucket);

    void setTitle(String title);
}
