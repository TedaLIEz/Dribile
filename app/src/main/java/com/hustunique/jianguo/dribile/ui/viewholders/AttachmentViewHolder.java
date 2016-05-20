package com.hustunique.jianguo.dribile.ui.viewholders;

import android.view.View;
import android.widget.ImageView;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.models.Attachment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JianGuo on 4/7/16.
 */
public class AttachmentViewHolder extends BaseViewHolder<Attachment> {
    @Bind(R.id.image_attachment)
    public ImageView mImage;
    public AttachmentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
