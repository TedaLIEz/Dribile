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

package com.hustunique.jianguo.mysearchview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by JianGuo on 11/7/16.
 * SearchView in material design style.
 */

public class MaterialSearchView extends FrameLayout implements View.OnClickListener {
    private Context mContext;
    private MenuItem mMenuItem;
    private int mAnimationDuration = SearchViewAnimator.DEFAULT_DURATION;
    private boolean mSearchOpen = false;
    private int mInitHeight;
    private int mInitWidth;
    protected CharSequence mOldQueryText;
    protected String mUserQuery = "";

    ImageView mBackImageView;
    ImageView mClearImageView;
    RecyclerView mSuggestions;
    EditText mInputEditText;
    LinearLayout mSearchLayout;

    private OnTextQueryListener mOnQueryListener;
    private OnOpenCloseListener mOnOpenCloseListener;

    public interface OnTextQueryListener {
        boolean onQueryTextSubmit(CharSequence query);

        boolean onQueryTextChange(CharSequence newText);
    }

    public interface OnOpenCloseListener {
        boolean onClose();

        boolean onOpen();
    }

    public MaterialSearchView(Context context) {
        this(context, null);
    }

    public MaterialSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        mContext = context;
        initView();
        initStyle(attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView();
        initStyle(attrs, defStyleAttr);
    }

    private void initStyle(AttributeSet attrs, int defStyleAttr) {

    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.search_view, this, true);
        mSearchLayout = (LinearLayout) findViewById(R.id.linearLayout_search);
        mSuggestions = (RecyclerView) findViewById(R.id.recyclerView_suggestions);
        mBackImageView = (ImageView) findViewById(R.id.imageView_arrow_back);
        mClearImageView = (ImageView) findViewById(R.id.imageView_clear);
        mInputEditText = (EditText) findViewById(R.id.editText_input);
        mClearImageView.setOnClickListener(this);
        mBackImageView.setOnClickListener(this);
        mSuggestions.setNestedScrollingEnabled(false);
        mSuggestions.setVisibility(View.GONE);
        // http://stackoverflow.com/questions/18277028/getheight-for-view-which-has-visibility-gone
        mSearchLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mInitHeight = mSearchLayout.getHeight();
                mInitWidth = mSearchLayout.getWidth();
                mSearchLayout.setVisibility(View.GONE);
                mSearchLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        initInput();
    }

    private void initInput() {
        mInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO: add more features at here.
                MaterialSearchView.this.handleChangedText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mInputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                handleSubmit();
                return true;
            }
        });
    }


    private void handleSubmit() {
        CharSequence query = mInputEditText.getText();
        if (query != null && TextUtils.getTrimmedLength(query) > 0) {
            // TODO: add query to filter to get suggestions.
            if (mOnQueryListener == null || !mOnQueryListener.onQueryTextSubmit(query)) {
                // move the cursor to the end of text
                // http://stackoverflow.com/a/37417170/4380801
                mInputEditText.setText("");
                mInputEditText.append(query);
            }
        }
    }

    private void handleChangedText(CharSequence s) {
        mClearImageView.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
        if (mOnQueryListener != null) {
            mOnQueryListener.onQueryTextChange(s);
        }
    }

    public void setMenuItem(MenuItem item) {
        mMenuItem = item;
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                open();
                return true;
            }
        });
    }


    public void setOnQueryListener(@Nullable OnTextQueryListener onTextQueryListener) {
        mOnQueryListener = onTextQueryListener;
    }

    private void open() {
        open(true);
    }


    public void open(boolean animated) {
        if (isSearchOpen()) {
            return;
        }
        if (animated) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // fixme: weird position when first open the searchView.
                revealOpen();
            } else {
                fadeOpen();
            }
        } else {
            mSearchLayout.setVisibility(View.VISIBLE);
        }
        mInputEditText.requestFocus();
        mSearchOpen = true;
    }

    private void fadeOpen() {
        SearchViewAnimator.fadeOpen(mSearchLayout,
                mAnimationDuration,
                mInputEditText,
                true,
                mOnOpenCloseListener);
    }

    private void fadeClose() {
        SearchViewAnimator.fadeClose(mSearchLayout,
                mAnimationDuration,
                mInputEditText,
                true,
                mOnOpenCloseListener);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void revealOpen() {
        SearchViewAnimator.revealOpen(mSearchLayout,
                mInitWidth,
                mInitHeight,
                mAnimationDuration,
                mContext,
                mInputEditText,
                true,
                mOnOpenCloseListener);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void revealClose() {
        SearchViewAnimator.revealClose(mSearchLayout,
                mAnimationDuration,
                mContext,
                mInputEditText,
                true,
                mOnOpenCloseListener);
    }

    public void close(boolean animated) {
        mInputEditText.getText().clear();
        if (animated) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                revealClose();
            } else {
                fadeClose();
            }
        } else {
            mSearchLayout.setVisibility(View.GONE);
        }
        mSearchOpen = false;
    }

    public boolean isSearchOpen() {
        return mSearchOpen;
    }

    public void setAnimationDuration(@IntRange(from = 0) int duration) {
        mAnimationDuration = duration;
    }

    @Override
    public void onClick(View v) {
        if (v == mClearImageView) {
            if (mInputEditText.length() > 0) {
                mInputEditText.getText().clear();
            }
        } else if (v == mBackImageView) {
            close(true);
        }
    }


}
