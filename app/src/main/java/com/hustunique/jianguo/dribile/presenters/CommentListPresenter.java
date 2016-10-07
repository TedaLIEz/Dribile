package com.hustunique.jianguo.dribile.presenters;

import android.net.Uri;
import android.text.TextUtils;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.am.MyAccountManager;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.Comments;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.presenters.strategy.GetCommentsByIdStrategy;
import com.hustunique.jianguo.dribile.service.DribbbleShotsService;
import com.hustunique.jianguo.dribile.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.utils.ObservableTransformer;
import com.hustunique.jianguo.dribile.views.CommentListView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by JianGuo on 5/6/16.
 * Presenter for listing comments
 */
public class CommentListPresenter extends BaseListPresenter<Comments, CommentListView> {
    private static final String TAG = "CommentListPresenter";
    private final Shots mShot;
    public CommentListPresenter(Shots shots) {
        super();
        this.mShot = shots;
        mLoadDel.setLoadStrategy(new GetCommentsByIdStrategy(shots.getId()));
        mLoadDel.setPerPage(100);
    }


    @Override
    public void getData() {
        refresh();
    }

    @Override
    public void refresh() {
        mLoadDel.loadData()
                .compose(new ObservableTransformer<List<Comments>>())
                .subscribe(new LoadingListSubscriber() {
            @Override
            public void onNext(List<Comments> commentses) {
                setModel(commentses);
            }
        });
    }

    @Override
    public void setModel(List<Comments> model) {
        super.setModel(model);
        if (mShot != null) {
            view().setTitle(String.format(AppData.getString(R.string.comments_title)
                    , mShot.getComments_count()));
            view().setSubTitle(String.format(AppData.getString(R.string.comments_subtitle)
                    , mShot.getTitle()
                    , mShot.getUser().getName()));
            view().setAvatar(Uri.parse(MyAccountManager.getCurrentUser().getAvatar_url()));
        }
    }

    public void sendComments(String comment) {
        if (comment != null && TextUtils.isEmpty(comment.trim())) return;
        ApiServiceFactory.createService(DribbbleShotsService.class)
                .addComment(mShot.getId(), comment)
                .compose(new ObservableTransformer<Comments>())
                .subscribe(new Subscriber<Comments>() {
                    @Override
                    public void onCompleted() {
                        Logger.i(TAG, "send comment success!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view().onAddCommentError(e);
                    }

                    @Override
                    public void onNext(Comments comments) {
                        view().onAddCommentSuccess(comments);
                    }
                });
    }



}
