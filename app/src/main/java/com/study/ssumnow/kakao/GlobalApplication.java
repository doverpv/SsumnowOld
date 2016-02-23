/**
 * Copyright 2014 Daum Kakao Corp.
 *
 * Redistribution and modification in source or binary forms are not permitted without specific prior written permission. 
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
package com.study.ssumnow.kakao;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.kakao.auth.KakaoSDK;
import com.kakao.sdk.sample.common.log.Logger;

/**
 * ?��?�?? 캐시�????��??�서 �?��?�기 ?�한 ?�플리�??�션 객체?�다.
 * 로그??기반 ?�플?�에???�용?�다.
 *
 * @author MJ
 */
public class GlobalApplication extends Application {
    private static volatile GlobalApplication instance = null;
    private static volatile Activity currentActivity = null;
    private ImageLoader imageLoader;

    public static Activity getCurrentActivity() {
        Logger.d("++ currentActivity : " + (currentActivity != null ? currentActivity.getClass().getSimpleName() : ""));
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }

    /**
     * singleton ?�플리�??�션 객체�??�는??
     * @return singleton ?�플리�??�션 객체
     */
    public static GlobalApplication getGlobalApplicationContext() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }

    /**
     * ?��?�?로더, ?��?�?캐시, ?�청 ?��? 초기?�한??
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        KakaoSDK.init(new KakaoSDKAdapter());

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            final LruCache<String, Bitmap> imageCache = new LruCache<String, Bitmap>(3);

            @Override
            public void putBitmap(String key, Bitmap value) {
                imageCache.put(key, value);
            }

            @Override
            public Bitmap getBitmap(String key) {
                return imageCache.get(key);
            }
        };

        imageLoader = new ImageLoader(requestQueue, imageCache);
    }

    /**
     * ?��?�?로더�?반환?�다.
     * @return ?��?�?로더
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * ?�플리�??�션 종료??singleton ?�플리�??�션 객체 초기?�한??
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
