package com.hustunique.jianguo.dribile.service.factories;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import rx.Observable;
import rx.Single;

/**
 * Created by JianGuo on 3/31/16.
 * Some bugs in adapter-rxjava-beta4
 */
final class SingleHelper {
    static CallAdapter<Single<?>> makeSingle(final CallAdapter<Observable<?>> callAdapter) {
        return new CallAdapter<Single<?>>() {
            @Override public Type responseType() {
                return callAdapter.responseType();
            }

            @Override public <R> Single<?> adapt(Call<R> call) {
                Observable<?> observable = callAdapter.adapt(call);
                return observable.toSingle();
            }
        };
    }
}
