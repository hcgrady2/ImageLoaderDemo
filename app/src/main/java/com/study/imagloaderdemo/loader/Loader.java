package com.study.imagloaderdemo.loader;

import com.study.imagloaderdemo.request.BitmapRequest;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

public interface Loader {
    /**
     * 加载图片
     *
     * @param request
     */
    void loadImage(BitmapRequest request);
}