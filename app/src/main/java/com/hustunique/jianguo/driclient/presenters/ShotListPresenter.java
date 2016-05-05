package com.hustunique.jianguo.driclient.presenters;

import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.presenters.strategy.GetAllShotsStrategy;
import com.hustunique.jianguo.driclient.presenters.strategy.ILoadDataStrategy;
import com.hustunique.jianguo.driclient.presenters.strategy.ILoadShotStrategy;
import com.hustunique.jianguo.driclient.views.ILoadListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JianGuo on 5/4/16.
 */
public class ShotListPresenter extends BaseListPresenter<Shots, ILoadListView<Shots>> {

    private ILoadShotStrategy mILoadDataStrategy;


    public ShotListPresenter(ILoadShotStrategy ILoadDataStrategy) {
        mILoadDataStrategy = ILoadDataStrategy;
    }

    @Deprecated
    public ShotListPresenter() {
        mILoadDataStrategy = new GetAllShotsStrategy();
    }

    public int getLoadingCount() {
        return mILoadDataStrategy.getLoadingCount();
    }

    @Override
    protected void loadData() {
        mILoadDataStrategy.loadData().subscribe(new LoadingListSubscriber() {
            @Override
            public void onNext(List<Shots> shotses) {
                setModel(shotses);
            }
        });
    }

    @Override
    protected void resetState() {
        super.resetState();
        if (model != null) {
            model = null;
        }
    }

    public void loadMore() {
        view().showLoadingMore();
        mILoadDataStrategy.loadMore().subscribe(new LoadingListSubscriber() {
            @Override
            public void onNext(List<Shots> shotses) {
                model.addAll(shotses);
                view().showData(model);
            }
        });
    }


    public void refresh() {
        loadData();
    }

    public void setLoadStrategy(ILoadShotStrategy loadStrategy) {
        mILoadDataStrategy = loadStrategy;
    }
}
