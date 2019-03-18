package com.study.imagloaderdemo.app;

import android.app.Application;

import com.study.imagloaderdemo.cache.DoubleCache;


/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DoubleCache cache = DoubleCache.getInstance(this);
    }
}
