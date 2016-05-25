package com.hustunique.jianguo.dribile.presenters;

import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.views.BucketView;

/**
 * Created by JianGuo on 5/5/16.
 * Presenter for buckets item.
 */
public class BucketPresenter extends BasePresenter<Buckets, BucketView> {

    @Override
    protected void updateView() {
        view().setCount(model.getShots_count());
        view().setName(model.getName());
    }



    public void onBucketClicked() {
        if (setupDone()) {
            view().goToDetailView(model);
        }
    }

    public boolean onBucketLongClick() {
        if (setupDone()) {
            return view().onLongClick(model);
        }
        return false;
    }
}