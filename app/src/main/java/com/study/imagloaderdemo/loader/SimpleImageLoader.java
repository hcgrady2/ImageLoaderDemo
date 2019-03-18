package com.study.imagloaderdemo.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.study.imagloaderdemo.config.DisplayConfig;
import com.study.imagloaderdemo.config.ImageLoaderConfig;
import com.study.imagloaderdemo.request.RequestQueue;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

public class SimpleImageLoader {



    /**
     * 配置文件
     */
    private ImageLoaderConfig config;
    /**
     * 请求队列
     */
    private RequestQueue mRequestQueue;

    /**
     * 单例对象
     */
    private static volatile SimpleImageLoader mInstance;

    private SimpleImageLoader() {

    }


    private SimpleImageLoader(ImageLoaderConfig imageLoaderConfig) {
        this.config = imageLoaderConfig;
        mRequestQueue = new RequestQueue(config.getThreadcount());
        //开启队列
        mRequestQueue.start();

    }

    /**
     * 获取单例方法
     *
     * @param config
     * @return
     */
    public static SimpleImageLoader getInstance(ImageLoaderConfig config) {
        if (mInstance == null) {
            synchronized (SimpleImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new SimpleImageLoader(config);
                }
            }
        }

        return mInstance;
    }

    /**
     * 第二次获取单例
     *
     * @return
     */
    public static SimpleImageLoader getInstance() {
        if (mInstance == null) {

            throw new UnsupportedOperationException("没有初始化");
        }

        return mInstance;
    }

    /**
     * @param imageView
     * @param uri       file开头
     */
    public void displayImage(ImageView imageView, String uri) {
        displayImage(imageView, uri, null, null);
    }

    public void displayImage(ImageView imageView, String uri, DisplayConfig displayconfig, ImageListener imageListener) {
        //实例化一个请求
    //    BitmapRequest bitmapRequest = new BitmapRequest(imageView, uri, displayconfig, imageListener);
//        添加到队列里边去
     //   mRequestQueue.addRequest(bitmapRequest);

    }

    public static interface ImageListener {
        void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }

    /**
     * 获取到全局配置
     *
     * @return
     */
    public ImageLoaderConfig getConfig() {
        return config;
    }


}
