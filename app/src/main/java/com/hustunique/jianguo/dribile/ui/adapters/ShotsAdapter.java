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
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.presenters.ShotPresenter;
import com.hustunique.jianguo.dribile.ui.activity.BaseActivity;
import com.hustunique.jianguo.dribile.ui.activity.ShotInfoActivity;
import com.hustunique.jianguo.dribile.ui.viewholders.ShotsViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by JianGuo on 4/4/16.
 * RecyclerView adapter for dribbble shots
 */
public class ShotsAdapter extends MvpRecyclerListAdapter<Shots, ShotPresenter, ShotsViewHolder> implements Filterable {
    private ItemFilter mFilter = new ItemFilter();
    private Context mContext;
    private Collection<Shots> filterData = new ArrayList<>();
    private Collection<Shots> originalData;
    private String searchKeyWords;
    @LayoutRes
    int layout;

    public ShotsAdapter(Context context, @LayoutRes int layout) {
        this.layout = layout;
        mContext = context;
    }

    @NonNull
    @Override
    protected ShotPresenter createPresenter(@NonNull Shots model) {
        ShotPresenter shotPresenter = new ShotPresenter();
        shotPresenter.setModel(model);
        shotPresenter.setHighLight(searchKeyWords);
        return shotPresenter;
    }

    @NonNull
    @Override
    protected Object getModelId(@NonNull Shots model) {
        return model.getId();
    }

    @Override
    public ShotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ShotsViewHolder shotsViewHolder = new ShotsViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        //// fixme: 4/15/16 Confusing colorFilter when doesn't set this to false
        shotsViewHolder.setIsRecyclable(false);
        shotsViewHolder.setListener(listener);
        return shotsViewHolder;
    }

    private ShotsViewHolder.OnShotClickListener listener;

    public void setOnItemClickListener(ShotsViewHolder.OnShotClickListener listener) {
        this.listener = listener;
    }


    @Override
    public Filter getFilter() {
        return mFilter;
    }


    private class ItemFilter extends Filter {
        private boolean init = true;
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results == null) return;
            // Only Collection<Shots> will be gotten here.
            @SuppressWarnings("unchecked")
            List<Shots> rst = (List<Shots>) results.values;
            int count = results.count;
//            if (count != filterData.size()) {
                filterData = rst;
                clearAndAddAll(filterData);
//            }


        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null && models != null) {
                if (init) {
                    originalData = new ArrayList<>(models);
                    init = false;
                }
                FilterResults results = new FilterResults();
                String filterString = constraint.toString().toLowerCase().trim();
                List<Shots> rst = new ArrayList<>();
                for (Shots shots : originalData) {
                    if (shots.getTitle().toLowerCase().contains(filterString)) {
                        rst.add(shots);
                    }
                }
                results.values = rst;
                results.count = rst.size();
                searchKeyWords = constraint.toString().toLowerCase();
                return results;
            }
            return null;

        }
    }

    @Override
    public void clearAndAddAll(Collection<Shots> data) {
        super.clearAndAddAll(data);
    }
}
