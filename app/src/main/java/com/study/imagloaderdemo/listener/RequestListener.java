package com.study.imagloaderdemo.listener;

import android.graphics.Bitmap;

/**
 * Created by hcw on 2019/3/13.
 * Copyright©hcw.All rights reserved.
 */

public interface RequestListener {

    public boolean onException();

    public boolean onResouceReady(Bitmap resource);
}
