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

package com.hustunique.jianguo.dribile.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import com.hustunique.jianguo.dribile.models.BaseBean;
import com.hustunique.jianguo.dribile.ui.viewholders.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JianGuo on 4/16/16.
 */
@Deprecated
public abstract class BaseFooterAdapter<T extends BaseBean> extends BaseAdapter {
    protected static int TYPE_FOOTER = 0;
    protected static int TYPE_DATA = 1;

    protected List<T> mData = new ArrayList<>();
    protected Context ctx;
    protected @LayoutRes int layout;
    protected @LayoutRes int footerLayout;
    public BaseFooterAdapter(Context ctx, @LayoutRes int layout, @LayoutRes int footerLayout) {
        this.ctx = ctx;
        this.layout = layout;
        this.footerLayout = footerLayout;
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent);
        } else if (viewType == TYPE_DATA){
            return onCreateViewHolder(parent);
        }
        throw new IllegalArgumentException("Unknown viewtype " + viewType);
    }

    protected abstract BaseViewHolder onCreateFooterViewHolder(ViewGroup parent);
    protected abstract BaseViewHolder onCreateViewHolder(ViewGroup parent);

    @Override
    public abstract void onBindViewHolder(BaseViewHolder holder, int position);

    public void clearData() {
        mData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooter(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_DATA;
        }
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public void setDataBefore(List<T> data) {
        clearData();
        mData.addAll(0, data);
        notifyDataSetChanged();
    }

    public void setDataAfter(List<T> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    protected boolean isFooter(int position) {
        return position == mData.size();
    }


}
