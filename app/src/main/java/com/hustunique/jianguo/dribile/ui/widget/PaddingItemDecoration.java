
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

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class PaddingItemDecoration extends RecyclerView.ItemDecoration {
    private int mDividerLeftSize;

    private int mDividerTopSize;

    private int mDividerRightSize;

    private int mDividerBottomSize;

    public PaddingItemDecoration(int size) {
        super();
        mDividerLeftSize = size;
        mDividerTopSize = size;
        mDividerRightSize = size;
        mDividerBottomSize = size;
    }

    public PaddingItemDecoration(int left, int top, int right, int bottom) {
        mDividerLeftSize = left;
        mDividerTopSize = top;
        mDividerRightSize = right;
        mDividerBottomSize = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.left = mDividerLeftSize;
        outRect.top = mDividerTopSize;
        outRect.right = mDividerRightSize;
        outRect.bottom = mDividerBottomSize;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
