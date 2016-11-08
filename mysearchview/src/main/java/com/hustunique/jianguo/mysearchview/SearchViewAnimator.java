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

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;

/**
 * Created by JianGuo on 11/7/16.
 * Animator Util for {@Link com.hustunique.jianguo.mysearchview.MaterialSearchView}
 */

public class SearchViewAnimator {
    public static final int DEFAULT_DURATION = 300;

    static void fadeIn(View view, int duration) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);
        view.setAnimation(anim);
        view.setVisibility(View.VISIBLE);
    }

    static void fadeOut(View view, int duration) {
        Animation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);

        view.setAnimation(anim);
        view.setVisibility(View.GONE);
    }




    private static int getRightX(View view, int x) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        Log.d("Animator", "X pos " + (location[0] + view.getWidth()));
        return location[0] + x;
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static void revealOpen(View view, int x, int y, int duration, Context context, final EditText editText, final boolean shouldClearOnOpen, final MaterialSearchView.OnOpenCloseListener listener) {

        int cx = getRightX(view, x);
        int cy = SearchViewUtil.getUpY(view);
        Point displaySize = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(displaySize);
        float finalRadius = (float) Math.hypot(Math.max(cx, displaySize.x - cx), cy);

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0.0f, finalRadius);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);
        anim.addListener(new Animator.AnimatorListener() { // new AnimatorListenerAdapter()
            @Override
            public void onAnimationStart(Animator animation) {
                if (listener != null) {
                    listener.onOpen();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (shouldClearOnOpen && editText.length() > 0) {
                    editText.getText().clear();
                }
                editText.requestFocus();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static void revealClose(final View view, int duration, Context context, final EditText editText, final boolean shouldClearOnClose, final MaterialSearchView.OnOpenCloseListener listener) {
        int cx = SearchViewUtil.getRightX(view);
        if (cx <= 0) {
            int padding = context.getResources().getDimensionPixelSize(R.dimen.search_reveal);
            if (SearchViewUtils.isRtlLayout(context))
                cx = padding;
            else
                cx = view.getWidth() - padding;
        }

        int cy = context.getResources().getDimensionPixelSize(R.dimen.search_height) / 2;

        if (cx != 0 && cy != 0) {
            Point displaySize = new Point();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(displaySize);
            float initialRadius = (float) Math.hypot(Math.max(cx, displaySize.x - cx), cy);

            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0.0f);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(duration);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (shouldClearOnClose && editText.length() > 0) {
                        editText.getText().clear();
                    }
                    editText.clearFocus();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                    if (listener != null) {
                        listener.onClose();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            anim.start();
        }
    }

    static void fadeOpen(View view, int duration, final EditText editText, final boolean shouldClearOnOpen, final MaterialSearchView.OnOpenCloseListener listener) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (listener != null) {
                    listener.onOpen();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (shouldClearOnOpen && editText.length() > 0) {
                    editText.getText().clear();
                }
                editText.requestFocus();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        view.setAnimation(anim);
        view.setVisibility(View.VISIBLE);
    }

    static void fadeClose(final View view, int duration, final EditText editText, final boolean shouldClearOnClose, final MaterialSearchView.OnOpenCloseListener listener) {
        Animation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(duration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (shouldClearOnClose && editText.length() > 0) {
                    editText.getText().clear();
                }
                editText.clearFocus();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                if (listener != null) {
                    listener.onClose();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        view.setAnimation(anim);
        view.setVisibility(View.GONE);
    }


}
