package com.study.imagloaderdemo.core;

import android.app.Activity;
import android.app.FragmentManager;

import com.study.imagloaderdemo.lifecycle.RequesManagerFragment;
import com.study.imagloaderdemo.request.BitmapRequest;

/**
 * Created by hcw on 2019/3/13.
 * Copyright©hcw.All rights reserved.
 */

public class Glide {
    public static BitmapRequest with(Activity activity){

        //不能没加载一个图片就绑定一个
        FragmentManager manager =  activity.getFragmentManager();

        RequesManagerFragment current = (RequesManagerFragment)manager.findFragmentByTag("com.study.imagloaderdemo");


        if (current == null){
           current = new RequesManagerFragment();

        }
        manager.beginTransaction().add(current,"com.study.imagloaderdemo").commitAllowingStateLoss();
        return new BitmapRequest(activity);
    }
}

