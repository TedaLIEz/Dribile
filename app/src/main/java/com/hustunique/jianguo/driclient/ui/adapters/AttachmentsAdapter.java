package com.hustunique.jianguo.driclient.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hustunique.jianguo.driclient.bean.Attachment;
import com.hustunique.jianguo.driclient.ui.viewholders.AttachmentViewHolder;
import com.hustunique.jianguo.driclient.ui.viewholders.BaseViewHolder;
import com.squareup.picasso.Picasso;

/**
 * Created by JianGuo on 4/7/16.
 * Simple Adapter for attachment.
 */
public class AttachmentsAdapter extends BaseDriListAdapter<Attachment> {
    private Context ctx;
    @LayoutRes
    int layout;

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
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        AttachmentViewHolder attachmentViewHolder = (AttachmentViewHolder) holder;
        Attachment attachment = getItem(position);
        Picasso.with(ctx).load(Uri.parse(attachment.getThumbnail_url())).into(attachmentViewHolder.mImage);
        super.onBindViewHolder(holder, position);
    }
}
