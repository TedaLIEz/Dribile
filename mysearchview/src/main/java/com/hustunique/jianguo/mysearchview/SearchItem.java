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

package com.hustunique.jianguo.mysearchview;

import android.support.annotation.NonNull;

/**
 * Created by JianGuo on 11/8/16.
 * SearchItem in {@link SearchAdapter}
 */

public class SearchItem implements Comparable<SearchItem> {
    final String keywords;
    int searchTimes;

    public SearchItem(String keywords) {
        this(keywords, 0);
    }

    public SearchItem(String keywords, int searchTimes) {
        this.keywords = keywords;
        this.searchTimes = searchTimes;
    }

    public int incrementAndGet() {
        searchTimes++;
        return searchTimes;
    }


    @Override
    public String toString() {
        return "SearchItem{" +
                "keywords='" + keywords + '\'' +
                ", searchTimes=" + searchTimes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchItem that = (SearchItem) o;

        return keywords.equals(that.keywords);

    }

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }

    @Override
    public int compareTo(@NonNull SearchItem another) {
        return Integer.compare(searchTimes, another.searchTimes);
    }


}
