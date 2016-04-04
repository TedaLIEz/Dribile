package com.hustunique.jianguo.driclient.ui.viewholders;

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
