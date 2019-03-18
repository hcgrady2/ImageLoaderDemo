package com.study.imagloaderdemo.lifecycle;

import android.app.Activity;
import android.app.Fragment;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

/**
 * 生命周期管理，观察者
 */
public class RequesManagerFragment extends Fragment {

    Activity mActivity;

    private int activityCode;

    LifecycleObservable lifecycleObservable = LifecycleObservable.getInstance();


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //mActivity = activity;
        activityCode  = activity.hashCode();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        lifecycleObservable.onDestroy(activityCode);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


}
