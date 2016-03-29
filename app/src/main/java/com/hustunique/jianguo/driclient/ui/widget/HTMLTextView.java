package com.hustunique.jianguo.driclient.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by JianGuo on 3/29/16.
 * Simple TextView supporting HTML
 */
public class HTMLTextView extends TextView{
    public HTMLTextView(Context context) {
        super(context);
    }

    public HTMLTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HTMLTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        String html = TextUtils.htmlEncode(String.valueOf(text));
        super.setText(html, type);
    }
}
