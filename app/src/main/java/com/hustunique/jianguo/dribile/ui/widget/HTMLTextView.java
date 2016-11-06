/*
 * Copyright (c) 2016.  TedaLIEz <aliezted@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        String rst = text.toString().replace("href=\"https://dribbble.com", "href=\"dribile://dribbble.com");

        Spanned spannable = Html.fromHtml(rst);
        setMovementMethod(LinkMovementMethod.getInstance());
        setClickable(true);
        super.setText(spannable, type);
    }
}
