package com.hustunique.jianguo.driclient.presenters;

import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.presenters.strategy.GetAllShotsStrategy;
import com.hustunique.jianguo.driclient.presenters.strategy.ILoadDataStrategy;
import com.hustunique.jianguo.driclient.presenters.strategy.LoadDataDelegate;
import com.hustunique.jianguo.driclient.views.ILoadListView;

import java.util.List;

/**
 * Created by JianGuo on 5/4/16.
 * Presenter for listing shots.
 */
public class ShotListPresenter extends BaseListPresenter<Shots, ILoadListView<Shots>> {



    public ShotListPresenter() {
        super();
        mLoadDel.setLoadStrategy(new GetAllShotsStrategy());
    }

    public void setLoadStrategy(ILoadDataStrategy<Shots> loadStrategy) {
        mLoadDel.setLoadStrategy(loadStrategy);
    }

    public int getLoadingCount() {
        return mLoadDel.getLoadTotal();
    }

    @Override
    protected void loadData() {
        mLoadDel.loadData().subscribe(new LoadingListSubscriber() {
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
        mLoadDel.loadMore().subscribe(new LoadingListSubscriber() {
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
}
