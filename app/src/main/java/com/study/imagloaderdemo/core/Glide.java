package com.study.imagloaderdemo.core;

import android.app.Activity;
import android.app.FragmentManager;
import android.util.Log;

import com.study.imagloaderdemo.lifecycle.RequestManagerFragment;
import com.study.imagloaderdemo.request.BitmapRequest;
import com.study.imagloaderdemo.utils.Constants;

/**
 * Created by hcw on 2019/3/13.
 * Copyright©hcw.All rights reserved.
 */

public class Glide {
    public static BitmapRequest with(Activity activity){

        //不能没加载一个图片就绑定一个
        FragmentManager manager =  activity.getFragmentManager();
        RequestManagerFragment current = (RequestManagerFragment)manager.findFragmentByTag("com.study.imagloaderdemo");

        if (current == null){
            current = new RequestManagerFragment();
            manager.beginTransaction().add(current,"com.study.imagloaderdemo").commitAllowingStateLoss();
        }
        return new BitmapRequest(activity);
    }
}

