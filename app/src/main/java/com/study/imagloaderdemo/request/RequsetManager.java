package com.study.imagloaderdemo.request;

import android.util.Log;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by hcw on 2019/3/13.
 * Copyright©hcw.All rights reserved.
 */

//有一个队列
public class RequsetManager {



    public RequsetManager() {

        start();
    }

    //转发器管理
    private BitmapDispatcher[] dispatchers;


    private void start() {
        stop();

        int threadCount = Runtime.getRuntime().availableProcessors();
        dispatchers = new BitmapDispatcher[threadCount];

        for (int i = 0; i < dispatchers.length; i++){
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(requestQueuue);
            bitmapDispatcher.start();
            dispatchers[i]  = bitmapDispatcher;
        }
    }

    private void stop(){
        if (dispatchers != null && dispatchers.length > 0){
            for (BitmapDispatcher bitmapDispatcher : dispatchers){
                if (!bitmapDispatcher.isInterrupted()){
                    bitmapDispatcher.interrupt();
                }
            }
        }
    }

    private static RequsetManager instance;
    public static RequsetManager getInstance(){
        if (instance == null){
            synchronized (RequsetManager.class){
                if (instance == null){
                    instance = new RequsetManager();
                }
            }
        }
        return  instance;
    }

    private LinkedBlockingDeque<BitmapRequest> requestQueuue = new LinkedBlockingDeque<>();

    //请求入队
    public void addBitmapRequest(BitmapRequest request){

        if (requestQueuue.contains(request)){
            requestQueuue.add(request);
        }else {
            Log.i("GlideDemo", "addBitmapRequest: 任务已经添加，不用重复添加");
        }
    }


}
