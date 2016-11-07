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

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.view.ViewCompat;

import java.util.Locale;

/**
 * Created by JianGuo on 11/7/16.
 */

public class SearchViewUtils {
    static boolean isRTL() {
        return isRTL(Locale.getDefault());
    }

    /*if (isRTL()) {
        // The view has RTL layout
        mSearchArrow.setDirection(SearchArrowDrawable.ARROW_DIRECTION_END);
    } else {
        // The view has LTR layout
        mSearchArrow.setDirection(SearchArrowDrawable.ARROW_DIRECTION_START);
    }*/

    private static boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }

    static boolean isRtlLayout(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && context.getResources().getConfiguration().getLayoutDirection() == ViewCompat.LAYOUT_DIRECTION_RTL;
    }

    public static boolean isLandscapeMode(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}
