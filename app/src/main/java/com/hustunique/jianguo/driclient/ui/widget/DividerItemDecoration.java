
package com.hustunique.jianguo.driclient.ui.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private int mDividerLeftSize;

    private int mDividerTopSize;

    private int mDividerRightSize;

    private int mDividerBottomSize;

    public DividerItemDecoration(int size) {
        super();
        mDividerLeftSize = size;
        mDividerTopSize = size;
        mDividerRightSize = size;
        mDividerBottomSize = size;
    }

    public DividerItemDecoration(int left, int top, int right, int bottom) {
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
