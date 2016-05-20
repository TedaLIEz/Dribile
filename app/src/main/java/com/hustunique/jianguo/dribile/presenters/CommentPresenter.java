package com.hustunique.jianguo.dribile.presenters;

import com.hustunique.jianguo.dribile.models.Comments;
import com.hustunique.jianguo.dribile.utils.CommonUtils;
import com.hustunique.jianguo.dribile.views.CommentView;

/**
 * Created by JianGuo on 5/4/16.
 * Presenter for each comments item
 */
public class CommentPresenter extends BasePresenter<Comments, CommentView> {
    @Override
    protected void updateView() {
        view().setAvatar(model.getUser().getAvatar_url());
        view().setBody(model.getBody());
        view().setName(model.getUser().getName());
        view().setTime(CommonUtils.currentTimeLine(model.getUpdated_at()));
        view().setLikeCount(model.getLikes_count());
    }

    public void onShotClicked() {
        if (setupDone()) {
            view().goToDetailView(model);
        }
    }


}
