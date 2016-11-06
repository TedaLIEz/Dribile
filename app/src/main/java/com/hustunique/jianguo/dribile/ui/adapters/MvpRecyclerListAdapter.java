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

import com.hustunique.jianguo.dribile.presenters.BasePresenter;
import com.hustunique.jianguo.dribile.ui.viewholders.MvpViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by JianGuo on 5/3/16.
 * Base ListAdapter for RecyclerView in MVP design pattern.
 */
public abstract class MvpRecyclerListAdapter<M, P extends BasePresenter, VH extends MvpViewHolder<P>> extends MvpRecyclerAdapter<M, P, VH> {
    private final List<M> models;

    public MvpRecyclerListAdapter() {
        models = new ArrayList<>();
    }

    public void clearAndAddAll(Collection<M> data) {
        models.clear();
        presenters.clear();
        for (M item : data) {
            addInternal(item);
        }
        notifyDataSetChanged();
    }

    public void addAll(Collection<M> data) {
        for (M item : data) {
            addInternal(item);
        }
        int addedSize = data.size();
        int oldSize = models.size() - addedSize;
        notifyItemRangeInserted(oldSize, addedSize);
    }

    public void addItem(M item, int pos) {
        addInternal(item, pos);
        notifyItemInserted(pos);

    }

    private void addInternal(M item, int pos) {
        models.add(pos, item);
        presenters.put(getModelId(item), createPresenter(item));
    }

    public void addItem(M item) {
        addInternal(item);
        notifyItemInserted(models.size());
    }

    public void updateItem(M item) {
        Object modelId = getModelId(item);
        int position = getItemPosition(item);
        if (position >= 0) {
            models.remove(position);
            models.add(position, item);
        }
        // Swap the presenter
        P existingPresenter = presenters.get(modelId);
        if (existingPresenter != null) {
            existingPresenter.setModel(item);
        }

        if (position >= 0) {
            notifyItemChanged(position);
        }
    }

    public void removeItem(int pos) {
        removeItem(getItem(pos));
    }

    public void removeItem(M item) {
        int position = getItemPosition(item);
        if (position >= 0) {
            models.remove(item);
        }
        presenters.remove(getModelId(item));

        if (position >= 0) {
            notifyItemRemoved(position);
        }
    }

    private int getItemPosition(M item) {
        Object modelId = getModelId(item);

        int position = -1;
        for (int i = 0; i < models.size(); i++) {
            M model = models.get(i);
            if (getModelId(model).equals(modelId)) {
                position = i;
                break;
            }
        }
        return position;
    }




    private void addInternal(M item) {
        models.add(item);
        presenters.put(getModelId(item), createPresenter(item));
    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    protected M getItem(int pos) {
        return models.get(pos);
    }
}
