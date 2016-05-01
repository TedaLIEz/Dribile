package com.hustunique.jianguo.driclient.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hustunique.jianguo.driclient.models.Attachment;
import com.hustunique.jianguo.driclient.ui.viewholders.AttachmentViewHolder;
import com.hustunique.jianguo.driclient.ui.viewholders.BaseViewHolder;
import com.squareup.picasso.Picasso;

/**
 * Created by JianGuo on 4/7/16.
 * Simple Adapter for attachment.
 */
@Deprecated
public class AttachmentsAdapter extends BaseDriListAdapter<Attachment> {
    private Context ctx;
    @LayoutRes
    int layout;

    public interface OnItemClickListener {
        void onClick(View v, Attachment attachment);
    }

    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AttachmentsAdapter() { throw new NullPointerException("You must give an context"); }

    public AttachmentsAdapter(Context ctx, @LayoutRes int layout) {
        this.ctx = ctx;
        this.layout = layout;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(layout, parent, false);
        return new AttachmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        AttachmentViewHolder attachmentViewHolder = (AttachmentViewHolder) holder;
        Attachment attachment = getItem(position);
        if (attachment.getThumbnail_url() != null) {
            Picasso.with(ctx).load(Uri.parse(attachment.getThumbnail_url()))
                    .into(attachmentViewHolder.mImage);
        }
        attachmentViewHolder.mImage.setOnClickListener(new View.OnClickListener() {
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
