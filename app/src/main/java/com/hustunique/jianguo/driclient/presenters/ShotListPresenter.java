package com.hustunique.jianguo.driclient.presenters;

import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.hustunique.jianguo.driclient.dao.ShotsDataHelper;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.presenters.strategy.GetAllShotsStrategy;
import com.hustunique.jianguo.driclient.presenters.strategy.ICacheDataStrategy;
import com.hustunique.jianguo.driclient.presenters.strategy.ILoadDataStrategy;
import com.hustunique.jianguo.driclient.presenters.strategy.LoadDataDelegate;
import com.hustunique.jianguo.driclient.views.ILoadListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

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
//        Observable.create(new Observable.OnSubscribe<List<Shots>>() {
//            @Override
//            public void call(Subscriber<? super List<Shots>> subscriber) {
//                subscriber.onNext(loadDB());
//                subscriber.onCompleted();
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<Shots>>() {
//                    @Override
//                    public void call(List<Shots> shotses) {
//                        Log.i("driclient", "load from db" );
//                        setModel(shotses);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.wtf("driclient", throwable);
//                    }
//                });
    }

    private List<Shots> loadDB() {
        Gson gson = new Gson();
        ShotsDataHelper helper = new ShotsDataHelper();
        Cursor cursor = helper.getList();
        cursor.moveToFirst();
        List<Shots> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            data.add(gson.fromJson(
                    cursor.getString(cursor.getColumnIndex(ShotsDataHelper.ShotsTable.JSON)),
                    Shots.class));
        }
        return data;
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
//        Observable.create(new Observable.OnSubscribe<Boolean>() {
//
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                subscriber.onNext(cache(model));
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean aBoolean) {
//                        Log.i("driclient", "cache data size " + model.size() + (aBoolean ? "success" : "failed"));
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.wtf("driclient", throwable);
//                    }
//                });
    }

    private boolean cache(List<Shots> shotses) {
        if (shotses != null && shotses.size() != 0) {
            ShotsDataHelper helper = new ShotsDataHelper();
            helper.deleteAll();
            helper.bulkInsert(shotses);
            return true;
        }
        return false;
    }
}
