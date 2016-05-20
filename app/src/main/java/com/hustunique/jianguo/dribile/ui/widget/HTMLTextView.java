package com.hustunique.jianguo.dribile.ui.widget;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
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
        // Better use Html.fromHtml to parse the html string
        Spanned spannable = Html.fromHtml(text.toString());
        setMovementMethod(LinkMovementMethod.getInstance());
        setClickable(true);
        super.setText(spannable, type);
    }
}
