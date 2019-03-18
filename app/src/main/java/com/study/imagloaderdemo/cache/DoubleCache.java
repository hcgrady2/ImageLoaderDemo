package com.study.imagloaderdemo.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.study.imagloaderdemo.request.BitmapRequest;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

public class DoubleCache implements BitmapCache {


    private static DoubleCache instance;


    public  static DoubleCache getInstance(Context context){
        if (instance == null){
            synchronized (DoubleCache.class){
                if (instance == null){
                    instance = new DoubleCache(context);
                }
            }
        }
        return instance;
    }

    public static DoubleCache getInstance(){
        if (instance == null){
            throw new RuntimeException("请在 application 初始化");
        }
        return instance;
    }


    //内存缓存
    private MemoryCache mMemoryCache = new MemoryCache();
    // 硬盘缓存
    private DiskCache mDiskCache;

    public DoubleCache(Context context) {
        mDiskCache = DiskCache.getInstance(context);
    }



    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        mMemoryCache.put(request, bitmap);
        mDiskCache.put(request, bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        Bitmap bitmap = mMemoryCache.get(request);
        if (bitmap == null) {
            bitmap = mDiskCache.get(request);
            if (bitmap != null) {
                //放入内存，方便获取
                mMemoryCache.put(request, bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public void remove(BitmapRequest request) {
        mMemoryCache.remove(request);
        mDiskCache.remove(request);
    }



    public void remove(int activityCode) {
        mMemoryCache.remove(activityCode);
       // mDiskCache.remove(request);
    }



}
