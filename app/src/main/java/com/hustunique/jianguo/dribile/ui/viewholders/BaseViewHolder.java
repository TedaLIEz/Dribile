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

package com.hustunique.jianguo.dribile.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by JianGuo on 4/4/16.
 * Skeleton class for recyclerview viewholders.
 */
public class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    private T t;


    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public View getItemView() {
        return itemView;
    }

    public void setData(T t) {
        this.t = t;
    }

    public T getData() {
        return t;
    }
}
