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

package com.hustunique.jianguo.dribile.presenters;

import android.net.Uri;

import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.service.GifImageLoader;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.utils.Utils;
import com.hustunique.jianguo.dribile.views.GifView;

/**
 * Created by JianGuo on 11/23/16.
 * Presenter for loading gif
 */

public class GifPresenter extends BasePresenter<Shots, GifView> {
    private static final String TAG = "GifPresenter";
    private GifImageLoader loader;

    @Override
    protected void updateView() {
        view().onBitmapLoaded(Uri.parse(model.getImages().getNormal()));
    }

    public GifPresenter(Shots shots) {
        loadImage(shots);
    }

    private void loadImage(Shots shots) {
        Logger.i(TAG, "loading url " + shots.getImages().getNormal());
        if (Utils.isGif(shots)) {
            loader = new GifImageLoader(AppData.getContext());
            loader.display(shots.getImages().getHidpi() == null ?
                    shots.getImages().getNormal() : shots.getImages().getHidpi()).callback(new GifImageLoader.Callback() {
                @Override
                public void onCompleted(byte[] bytes) {
                    view().onGifLoaded(bytes);
                }


                //TODO: Replace with a placeholder
                @Override
                public void onFailed() {
                    view().onFailed();
                }
            });
        } else {
            setModel(shots);
        }
    }


}
