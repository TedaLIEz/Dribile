package com.hustunique.jianguo.driclient.presenters;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.app.UserManager;
import com.hustunique.jianguo.driclient.models.Comments;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.presenters.strategy.GetCommentsByIdStrategy;
import com.hustunique.jianguo.driclient.service.DribbbleShotsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.driclient.views.CommentListView;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JianGuo on 5/6/16.
 * Presenter for listing comments
 */
public class CommentListPresenter extends BaseListPresenter<Comments, CommentListView> {
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
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
            view().setAvatar(Uri.parse(UserManager.getCurrentUser().getUser().getAvatar_url()));
        }
    }

    public void sendComments(String comment) {
        if (comment != null && TextUtils.isEmpty(comment.trim())) return;
        ApiServiceFactory.createService(DribbbleShotsService.class)
                .addComment(mShot.getId(), comment)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Comments>() {
                    @Override
                    public void onCompleted() {
                        Log.i("driclient", "send comment success!");
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
