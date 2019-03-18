package com.study.imagloaderdemo.loader;

import android.graphics.Bitmap;

import com.study.imagloaderdemo.request.BitmapRequest;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

//当然如果你既不是本地的地址，也不是网络地址，就返回空的加载实现  NullLoader.java


public class NullLoader extends AbstarctLoader {

    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        return null;
    }
}