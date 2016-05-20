package com.hustunique.jianguo.dribile.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hustunique.jianguo.dribile.presenters.BasePresenter;

/**
 * Created by JianGuo on 5/3/16.
 */
public class MvpViewHolder<P extends BasePresenter> extends RecyclerView.ViewHolder {
    protected P presenter;

    public MvpViewHolder(View itemView) {
        super(itemView);
    }

    public void bindPresenter(P presenter) {
        this.presenter = presenter;
        presenter.bindView(this);
    }

    public void unbindPresenter() {
        this.presenter = null;
    }

}
