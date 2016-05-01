package com.hustunique.jianguo.driclient.ui.adapters;

import android.view.ViewGroup;

import com.hustunique.jianguo.driclient.models.BaseBean;
import com.hustunique.jianguo.driclient.ui.viewholders.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JianGuo on 4/4/16.
 * Base listAdapter for dribbble data.
 */
public class BaseDriListAdapter<T extends BaseBean> extends BaseAdapter {

    protected List<T> mData = new ArrayList<>();

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public void clearData() {
        int preSize = mData.size();
        mData.clear();
//        notifyItemRangeRemoved(0, preSize);
        notifyDataSetChanged();
    }



    public void removeData(T data) {
        mData.remove(data);
        notifyDataSetChanged();
    }

    public void addData(T data) {
        mData.add(0, data);
        notifyDataSetChanged();
    }

    public void setDataBefore(List<T> data) {
        clearData();

        mData.addAll(0, data);
        notifyItemRangeInserted(0, data.size());
    }

    public void setDataAfter(List<T> data) {
        int oldPos = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(oldPos, data.size());
//        notifyDataSetChanged();
    }
}
