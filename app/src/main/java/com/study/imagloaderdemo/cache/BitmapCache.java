package com.study.imagloaderdemo.cache;

import android.graphics.Bitmap;

import com.study.imagloaderdemo.request.BitmapRequest;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

public interface BitmapCache {
    /**
     * 缓存bitmap
     * @param request
     * @param bitmap
     */
    void put(BitmapRequest request, Bitmap bitmap);

    /**
     * 通过请求去bitmap
     * @param request
     * @return
     */
    Bitmap get(BitmapRequest request);

    /**
     * 移除bitmap
     * @param request
     */
    void remove(BitmapRequest request);
}
