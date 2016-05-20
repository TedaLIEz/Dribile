package com.hustunique.jianguo.dribile.presenters;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * Created by JianGuo on 5/1/16.
 * Base Presenter class, using in MVP design pattern
 */
public abstract class BasePresenter<M, V> {
    protected M model;
    private WeakReference<V> view;

    public void setModel(M model) {
        resetState();
        this.model = model;
        if (setupDone()) {
            updateView();
        }
    }

    protected void resetState() {

    }

    public void bindView(@NonNull V view) {
        this.view = new WeakReference<V>(view);
        if (setupDone()) {
            updateView();
        }
    }

    public void unbindView() {
        this.view = null;
    }

    protected V view() {
        return view == null ? null : view.get();
    }

    protected abstract void updateView();

    protected boolean setupDone() {
        return view() != null && model != null;
    }

}
