package com.hustunique.jianguo.driclient.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.models.Comments;
import com.hustunique.jianguo.driclient.ui.viewholders.BaseViewHolder;
import com.hustunique.jianguo.driclient.ui.viewholders.CommentsViewHolder;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by JianGuo on 4/6/16.
 *
 */
public class CommentsAdapter extends BaseDriListAdapter<Comments> {
    private Context mContext;

    @LayoutRes
    int layout;


    public interface OnItemClickListener {
        void onClick(View v, Comments comments);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public CommentsAdapter() { throw new NullPointerException("You must give an context"); }

    public CommentsAdapter(Context ctx, @LayoutRes int layout) {
        mContext = ctx;
        this.layout = layout;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layout, parent, false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        CommentsViewHolder commentsViewHolder = (CommentsViewHolder) holder;
        Comments comments = getItem(position);
        commentsViewHolder.setData(comments);

        commentsViewHolder.mCommentTime.setText(CommonUtils.currentTimeLine(comments.getUpdated_at()));
        commentsViewHolder.mUsername.setText(comments.getUser().getName());
        commentsViewHolder.mLikeCount.setText(comments.getLikes_count());
        commentsViewHolder.mCommentBody.setText(comments.getBody());
        Picasso.with(mContext)
                .load(Uri.parse(comments.getUser().getAvatar_url()))
                .placeholder(AppData.getDrawable(R.drawable.avatar_default))
                .into(commentsViewHolder.mAvatar);
        commentsViewHolder.mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, getItem(holder.getAdapterPosition()));
                }
            }
        });
        super.onBindViewHolder(holder, position);
    }
}
