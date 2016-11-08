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

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by JianGuo on 11/7/16.
 * SearchView in material design style.
 */
// TODO: 11/8/16 Change Layout to material design spec
public class MaterialSearchView extends FrameLayout implements View.OnClickListener {
    private Context mContext;
    private MenuItem mMenuItem;
    private int mAnimationDuration = SearchViewAnimator.DEFAULT_DURATION;
    private int LAYOUT_TRANSITION_DURATION = 300;
    private boolean mSearchOpen = false;
    private int mInitHeight;
    private int mInitWidth;
    protected SearchAdapter mAdapter;
    protected String mUserQuery = "";

    private static Typeface mTextFont = Typeface.DEFAULT;
    private int mTextStyle;
    private
    @ColorInt
    int mTextColor;
    private
    @ColorInt
    int mBackgroundColor;
    private
    @ColorInt
    int mHintColor;

    private float mTextSize;
    private String mHint;

    PopupWindow mPopWindow;
    ImageView mBackImageView;
    ImageView mClearImageView;
    RecyclerView mSuggestions;
    EditText mInputEditText;
    LinearLayout mSearchLayout;
    LinearLayout mPopupLayout;

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
        initStyle(attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initStyle(attrs, defStyleAttr);
        initView();
    }

    private void initStyle(AttributeSet attrs, int defStyleAttr) {
        // TODO: 11/8/16 Customize style
        TypedArray attr = mContext.obtainStyledAttributes(attrs, R.styleable.MaterialSearchView, defStyleAttr, 0);
        if (attr.hasValue(R.styleable.MaterialSearchView_search_hint)) {
            setHint(attr.getString(R.styleable.MaterialSearchView_search_hint));
        }
        setTextSize(attr.getDimension(R.styleable.MaterialSearchView_search_text_size,
                getResources().getDimension(R.dimen.searchView_text_size)));
        setHintColor(attr.getColor(R.styleable.MaterialSearchView_search_hint_color,
                getResources().getColor(R.color.searchView_text_hint)));
        setSearchBackgroundColor(attr.getColor(R.styleable.MaterialSearchView_search_background_color,
                getResources().getColor(R.color.searchView_bg)));
        setAnimationDuration(attr.getInt(R.styleable.MaterialSearchView_search_animation_duration,
                SearchViewAnimator.DEFAULT_DURATION));
        setTextStyle(attr.getInt(R.styleable.MaterialSearchView_search_text_style, 0));
        setTextColor(attr.getColor(R.styleable.MaterialSearchView_search_text_color,
                getResources().getColor(R.color.searchView_text)));
        attr.recycle();
    }


    private void setTextColor(@ColorInt int color) {
        mTextColor = color;

    }

    private void setTextStyle(int style) {
        mTextStyle = style;

    }

    private void setSearchBackgroundColor(@ColorInt int color) {
        mBackgroundColor = color;

    }

    private void setHintColor(@ColorInt int color) {
        mHintColor = color;

    }

    private void setTextSize(float size) {
        float scaleRatio = mContext.getResources().getDisplayMetrics().density;
        mTextSize = size / scaleRatio;

    }

    private void setHint(String string) {
        mHint = string == null ? "" : string;
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.search_view, this, true);
        mSearchLayout = (LinearLayout) findViewById(R.id.linearLayout_search);
        mBackImageView = (ImageView) findViewById(R.id.imageView_arrow_back);
        mClearImageView = (ImageView) findViewById(R.id.imageView_clear);
        mInputEditText = (EditText) findViewById(R.id.editText_input);

        mClearImageView.setOnClickListener(this);
        mBackImageView.setOnClickListener(this);
        initPopupView();

        initSearchLayout();
        initInput();
    }

    private void initSearchLayout() {
        mSearchLayout.setBackgroundColor(mBackgroundColor);
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
    }

    private void initPopupView() {
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.popup_layout, null, false);
        mSuggestions = (RecyclerView) popupView.findViewById(R.id.recyclerView_suggestions);
        mPopupLayout = (LinearLayout) popupView.findViewById(R.id.popup_view_shadow);
        mPopupLayout.setOnClickListener(this);
        mAdapter = new SearchAdapter(mContext);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String text) {
                Log.d("MaterialSearchView", "Click on item: " + text);
                handleSubmit(text);
            }
        });
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mPopWindow = new PopupWindow(popupView, size.x, WindowManager.LayoutParams.MATCH_PARENT, false);
        mPopWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_hg));
        mPopWindow.setOutsideTouchable(false);
        mSuggestions.setNestedScrollingEnabled(false);
        mSuggestions.setLayoutManager(new LinearLayoutManager(mContext));
        mSuggestions.setItemAnimator(null);
        mSuggestions.addItemDecoration(new SearchViewDivider(mContext));
        mSuggestions.setAdapter(mAdapter);
    }


    private LayoutTransition getRecyclerViewLayoutTransition() {
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setDuration(LAYOUT_TRANSITION_DURATION);
        return layoutTransition;
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mInputEditText, 0);
        imm.showSoftInput(this, 0);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindowToken(), 0);
    }

    private void initInput() {
        mInputEditText.setTypeface((Typeface.create(mTextFont, mTextStyle)));
        mInputEditText.setTextColor(mTextColor);
        mInputEditText.setHint(mHint);
        mInputEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        mInputEditText.setHintTextColor(mHintColor);
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
                if (event.getAction() != KeyEvent.ACTION_DOWN) {
                    return false;
                }
                handleSubmit(mInputEditText.getText());
                return true;
            }
        });

        mInputEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    if (mPopWindow.isShowing()) {
                        hideSuggestions();
                    } else {
                        showSuggestions();
                    }
                }
                return true; // return is important...
            }
        });
    }


    private void handleSubmit(CharSequence query) {
        if (query != null && TextUtils.getTrimmedLength(query) > 0) {
            // TODO: add query to filter to get suggestions.
            if (mOnQueryListener == null || !mOnQueryListener.onQueryTextSubmit(query)) {
                // move the cursor to the end of text
                // http://stackoverflow.com/a/37417170/4380801
                mInputEditText.setText("");
                mInputEditText.append(query);
            }
            close(true);
            mAdapter.addItem(new SearchItem(query.toString()));
        }
    }

    private void handleChangedText(CharSequence s) {
        mUserQuery = s.toString();
        if (mAdapter != null) {
            mAdapter.getFilter().filter(s);
        }
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

    public void setOnOpenCloseListener(@Nullable OnOpenCloseListener onOpenCloseListener) {
        mOnOpenCloseListener = onOpenCloseListener;
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
                revealOpen();
            } else {
                fadeOpen();
            }
        } else {
            mSearchLayout.setVisibility(View.VISIBLE);
        }
        showSuggestions();
        showKeyboard();
        mInputEditText.requestFocus();
        mSearchOpen = true;
    }

    public void showSuggestions() {
        if (mAdapter != null && mAdapter.getItemCount() > 0) {
            mPopWindow.showAsDropDown(mSearchLayout);
        }
    }

    public void hideSuggestions() {
        if (mAdapter != null) {
            mPopWindow.dismiss();
        }
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
        hideSuggestions();
        hideKeyboard();
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
        } else if (v == mPopupLayout) {
            close(true);
        }
    }


}
