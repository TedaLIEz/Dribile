package com.hustunique.jianguo.driclient.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.bean.BaseBean;
import com.hustunique.jianguo.driclient.ui.viewholders.BaseViewHolder;

import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JianGuo on 4/16/16.
 */
public abstract class BaseFooterAdapter<T extends BaseBean> extends BaseAdapter {
    private static int TYPE_FOOTER = 0;
    private static int TYPE_DATA = 1;

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
        return mData == null ? 1 : mData.size() + 1;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent);
        } else {
            return onCreateViewHolder(parent);
        }

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
        return mData.get(position - 1);
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

    private boolean isFooter(int position) {
        return position == getItemCount();
    }


}
