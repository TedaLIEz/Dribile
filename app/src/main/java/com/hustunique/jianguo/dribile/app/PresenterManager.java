package com.hustunique.jianguo.dribile.app;

import android.os.Bundle;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hustunique.jianguo.dribile.presenters.BasePresenter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by JianGuo on 5/1/16.
 * Manager for Presenter for mvp design
 */
public class PresenterManager {
    private static final String SIS_KEY_PRESENTER_ID = "presenter_id";
    private static PresenterManager instance;

    private final AtomicLong currentId;

    private final Cache<Long, BasePresenter<?, ?>> presenters;

    PresenterManager(long maxSize, long expirationValue, TimeUnit expirationUnit) {
        currentId = new AtomicLong();
        presenters = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expirationValue, expirationUnit)
                .build();
    }

    public static PresenterManager getInstance() {
        if (instance == null) {
            instance = new PresenterManager(10, 30, TimeUnit.SECONDS);
        }
        return instance;
    }

    public <P extends BasePresenter<?, ?>> P restorePresenter(Bundle savedInstanceState) {
        Long presenterId = savedInstanceState.getLong(SIS_KEY_PRESENTER_ID);
        //Cache is only used for BasePresenter and P is subclass of BasePresenter,
        //so the cast is acceptable here.
        @SuppressWarnings("unchecked")
        P presenter = (P) presenters.getIfPresent(presenterId);
        presenters.invalidate(presenterId);
        return presenter;
    }


    public void savePresenter(BasePresenter<?, ?> presenter, Bundle outState) {
        long presenterId = currentId.incrementAndGet();
        presenters.put(presenterId, presenter);
        outState.putLong(SIS_KEY_PRESENTER_ID, presenterId);
    }
}