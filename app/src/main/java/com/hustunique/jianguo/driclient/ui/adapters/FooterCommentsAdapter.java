package com.hustunique.jianguo.driclient.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hustunique.jianguo.driclient.models.Comments;
import com.hustunique.jianguo.driclient.ui.viewholders.BaseViewHolder;
import com.hustunique.jianguo.driclient.ui.viewholders.CommentsFooterViewHolder;
import com.hustunique.jianguo.driclient.ui.viewholders.CommentsViewHolder;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by JianGuo on 4/16/16.
 */
@Deprecated
public class FooterCommentsAdapter extends BaseFooterAdapter<Comments> {
    private static final int TOTAL_COMMENTS = 8;

    public FooterCommentsAdapter(Context ctx, @LayoutRes int layout, @LayoutRes int footerLayout) {
        super(ctx, layout, footerLayout);
    }


    public interface OnItemClickListener {
        void onClick(View v, Comments comments);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected BaseViewHolder onCreateFooterViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(ctx).inflate(footerLayout, parent, false);
        return new CommentsFooterViewHolder(view);
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(ctx).inflate(layout, parent, false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        if (position == mData.size() && holder.getItemViewType() == TYPE_FOOTER ) {
            if (mData.size() != 0) {
                CommentsFooterViewHolder commentsFooterViewHolder = (CommentsFooterViewHolder) holder;
                commentsFooterViewHolder.getItemView().setOnClickListener(footerClickListener);
            }

        } else if (holder.getItemViewType() == TYPE_DATA){
            CommentsViewHolder commentsViewHolder = (CommentsViewHolder) holder;
            Comments comments = getItem(position);
            commentsViewHolder.setData(comments);
            commentsViewHolder.mCommentTime.setText(CommonUtils.currentTimeLine(comments.getUpdated_at()));
            commentsViewHolder.mUsername.setText(comments.getUser().getName());
            commentsViewHolder.mLikeCount.setText(comments.getLikes_count());
            commentsViewHolder.mCommentBody.setText(comments.getBody());
            Picasso.with(ctx)
                    .load(Uri.parse(comments.getUser().getAvatar_url()))
                    .into(commentsViewHolder.mAvatar);
            commentsViewHolder.mAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(v, getItem(holder.getAdapterPosition()));
                    }
                }
            });
        }
    }




    private View.OnClickListener footerClickListener;
    public void setFooterClickListener(View.OnClickListener onClickListener) {
        footerClickListener = onClickListener;
    }
}
