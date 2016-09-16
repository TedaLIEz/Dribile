package com.hustunique.jianguo.dribile.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JianGuo on 9/28/16.
 */
public class ObservableTransformer<T> implements Observable.Transformer<T, T> {

    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
