package com.hustunique.jianguo.dribile.presenters;

import android.test.ActivityUnitTestCase;

import com.hustunique.jianguo.dribile.models.Comments;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.presenters.strategy.GetCommentsByIdStrategy;
import com.hustunique.jianguo.dribile.service.DribbbleShotsService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.dribile.views.ShotInfoCommentView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by JianGuo on 5/7/16.
 * Presenter for comments in ShotInfoActivity
 */
public class ShotInfoCommentsPresenter extends BaseListPresenter<Comments, ShotInfoCommentView> {
    private Shots mShot;
    private static final int COMMENTS_PER_PAGE = 5;

    public ShotInfoCommentsPresenter(Shots shots) {
        super();
        this.mShot = shots;
        mLoadDel.setLoadStrategy(new GetCommentsByIdStrategy(shots.getId()));
        mLoadDel.setPerPage(COMMENTS_PER_PAGE);
    }

    public ShotInfoCommentsPresenter(String id) {
        super();
        ApiServiceFactory.createService(DribbbleShotsService.class)
                .getShotById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<Shots>() {
                    @Override
                    public void call(Shots shots) {
                        ShotInfoCommentsPresenter.this.mShot = shots;

                    }
                });
        mLoadDel.setLoadStrategy(new GetCommentsByIdStrategy(id));
        mLoadDel.setPerPage(COMMENTS_PER_PAGE);
    }

    @Override
    protected void updateView() {
        if (Integer.parseInt(mShot.getComments_count()) >= COMMENTS_PER_PAGE) {
            view().showLoadingMore();
        }
        if (model.size() == 0) view().showEmpty();
        else view().showData(model);

    }

    @Override
    public void getData() {
        refresh();
    }

    @Override
    public void refresh() {
        mLoadDel.loadData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadingListSubscriber() {
            @Override
            public void onNext(List<Comments> commentses) {
                setModel(commentses);
            }
        });
    }


    public void goToMoreComments() {
        view().goToMoreComments(mShot);
    }


}
