package com.hustunique.jianguo.driclient.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.hustunique.jianguo.driclient.views.ILoadListView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import rx.Subscriber;

/**
 * Created by JianGuo on 5/4/16.
 */
public abstract class BaseListPresenter<M, V extends ILoadListView<M>> extends BasePresenter<List<M>, V> {
    protected static final String ARG_SORT_TYPE = "sort";
    //Sort type
    public static final String SORT_COMMENTS = "comments";
    public static final String SORT_RECENT = "recent";
    public static final String SORT_VIEWS = "views";

    protected boolean isLoadingData = false;

    @StringDef({SORT_COMMENTS, SORT_RECENT, SORT_VIEWS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SortType {
    }

    @Override
    protected void updateView() {
        if (model.size() == 0) view().showEmpty();
        else view().showData(model);
    }

    @Override
    public void bindView(@NonNull V view) {
        super.bindView(view);
        if (model == null && !isLoadingData) {
            view.showLoading();
            loadData();
        }
    }





    protected abstract void loadData();


    @Override
    protected void resetState() {
        super.resetState();
        isLoadingData = !isLoadingData;
    }


    public abstract class LoadingListSubscriber extends Subscriber<List<M>> {

        @Override
        public void onCompleted() {
            isLoadingData = false;
        }

        @Override
        public void onError(Throwable e) {
            view().onError(e);
        }
    }
}
