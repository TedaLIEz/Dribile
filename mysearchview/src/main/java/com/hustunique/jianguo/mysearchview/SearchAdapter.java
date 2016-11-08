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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JianGuo on 11/8/16.
 * Adapter holding search suggestions.
 */
// TODO: 11/8/16 Save search history!
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {
    private Context mContext;
    private List<SearchItem> mSuggestions;
    private List<SearchItem> mfilterData;
    private OnItemClickListener mOnItemClickListener;

    public SearchAdapter(Context context) {
        mContext = context;
        mSuggestions = new ArrayList<>();
    }

    public SearchAdapter(Context context, List<SearchItem> suggestions) {
        mSuggestions = suggestions;
        mfilterData = suggestions;
    }


    public void addItem(SearchItem item) {
        if (mSuggestions.contains(item)) {
            mSuggestions.get(mSuggestions.indexOf(item)).incrementAndGet();
            return;
        }
        mSuggestions.add(item);
        notifyItemInserted(mSuggestions.size());
    }


    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchAdapter.ViewHolder holder, int position) {
        holder.mSuggestion.setText(mSuggestions.get(position).keywords);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, mSuggestions.get(holder.getAdapterPosition()).keywords);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSuggestions.size();
    }


    @Override
    public Filter getFilter() {
        return new ItemFilter();
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, String text);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mSuggestion;

        public ViewHolder(View itemView) {
            super(itemView);
            mSuggestion = (TextView) itemView.findViewById(R.id.textView_suggestion);
        }

    }
}
