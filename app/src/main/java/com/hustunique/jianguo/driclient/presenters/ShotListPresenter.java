package com.hustunique.jianguo.driclient.presenters;

import android.util.Log;

import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.presenters.strategy.GetAllShotsStrategy;
import com.hustunique.jianguo.driclient.presenters.strategy.ICacheDataStrategy;
import com.hustunique.jianguo.driclient.presenters.strategy.ILoadDataStrategy;
import com.hustunique.jianguo.driclient.views.ILoadListView;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by JianGuo on 5/4/16.
 * Presenter for listing shots.
 */
public class ShotListPresenter extends BaseListPresenter<Shots, ILoadListView<Shots>> {

    private boolean cached = false;
    private boolean loadFromCache = true;
    public ShotListPresenter() {
        super();
        GetAllShotsStrategy getAllShotsStrategy = new GetAllShotsStrategy();
        setLoadStrategy(getAllShotsStrategy);
        setCacheStrategy(getAllShotsStrategy );
    }

    public void setLoadStrategy(ILoadDataStrategy<Shots> loadStrategy) {
        mLoadDel.setLoadStrategy(loadStrategy);
    }

    public void setCacheStrategy(ICacheDataStrategy<Shots> cacheStrategy) {
        mLoadDel.setCacheStrategy(cacheStrategy);
        setCached();
    }

    public void setCached() {
        cached = true;
    }

    public int getLoadingCount() {
        return mLoadDel.getLoadTotal();
    }

    @Override
    protected void loadData() {
        Log.i("driclient", "load data executed");
        if (cached && loadFromCache) {
            loadFromDB();
            loadFromCache = false;
        }
        mLoadDel.loadData().subscribe(new LoadingListSubscriber() {
            @Override
            public void onNext(List<Shots> shotses) {
                setModel(shotses);
                if (cached) {
                    saveToDB();
                }
            }
        });


    }

    private void loadFromDB() {
        mLoadDel.loadFromDB().subscribe(new Action1<List<Shots>>() {
            @Override
            public void call(List<Shots> shotses) {
                Log.i("driclient", "load from db" );
                setModel(shotses);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.wtf("driclient", throwable);
            }
        });
    }

    @Override
    protected void resetState() {
        super.resetState();
        if (model != null) {
            model.clear();
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

    public void saveToDB() {
        mLoadDel.cache(model).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                Log.i("driclient", "cache data size " + model.size() + (aBoolean ? "success" : "failed"));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.wtf("driclient", throwable);
            }
        });
    }
}
