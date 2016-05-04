package com.hustunique.jianguo.driclient.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.hustunique.jianguo.driclient.views.IListView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by JianGuo on 5/4/16.
 */
public abstract class BaseListPresenter<M, V extends IListView<M>> extends BasePresenter<List<M>, V> {
    protected static final String ARG_SORT_TYPE = "sort";
    //Sort type
    public static final String SORT_COMMENTS = "comments";
    public static final String SORT_RECENT = "recent";
    public static final String SORT_VIEWS = "views";

    private boolean isLoadingData = false;

    @StringDef({SORT_COMMENTS, SORT_RECENT, SORT_VIEWS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SortType {
    }


    @Override
    public void bindView(@NonNull V view) {
        super.bindView(view);
        if (model == null && !isLoadingData) {
            view.showLoading();
            loadData(isLoadingData);
        }
    }


    protected abstract void loadData(boolean isLoadingData);


    @Override
    protected void resetState() {
        super.resetState();
        isLoadingData = !isLoadingData;
    }
}
