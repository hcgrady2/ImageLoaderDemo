package com.study.imagloaderdemo.lifecycle;

import android.util.Log;

import com.study.imagloaderdemo.cache.DoubleCache;
import com.study.imagloaderdemo.utils.Constants;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

public class LifecycleObservable {

    private static LifecycleObservable instance;

    public static LifecycleObservable getInstance(){
        if (instance == null){
            synchronized (LifecycleObservable.class){
                if (instance == null){
                    instance = new LifecycleObservable();
                }
            }
        }
        return instance;
    }


    public void onStart(int activityCode){

    }

    public void onStop(int activityCode){

    }

    public void onDestroy(int activityCode){
        DoubleCache.getInstance().remove(activityCode);
    }


}
