package com.hustunique.jianguo.driclient.presenters;

import com.hustunique.jianguo.driclient.models.Comments;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.presenters.strategy.GetCommentsByIdStrategy;
import com.hustunique.jianguo.driclient.views.ShotInfoCommentView;

import java.util.List;

/**
 * Created by JianGuo on 5/7/16.
 * Presenter for comments in ShotInfoActivity
 */
public class ShotInfoCommentsPresenter extends BaseListPresenter<Comments, ShotInfoCommentView> {
    private final Shots mShot;
    private static final int COMMENTS_PER_PAGE = 5;

    public ShotInfoCommentsPresenter(Shots shots) {
        super();
        this.mShot = shots;
        mLoadDel.setLoadStrategy(new GetCommentsByIdStrategy(shots.getId()));
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
    protected void loadData() {
        mLoadDel.loadData().subscribe(new LoadingListSubscriber() {
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
