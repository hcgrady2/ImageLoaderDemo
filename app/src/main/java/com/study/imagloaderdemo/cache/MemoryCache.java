package com.study.imagloaderdemo.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.study.imagloaderdemo.request.BitmapRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

public class MemoryCache implements BitmapCache {

    private  LruCache<String, Bitmap> mLruCache;

    //图片的 md5 -> activityCode
    private  HashMap<String,Integer> activityCache;


    private static volatile MemoryCache instance;

    private static final byte[] lock = new byte[0];

    public static MemoryCache getInstance(){
        if (instance == null){
            synchronized (lock){
                if (instance == null){
                    instance = new MemoryCache();
                }
            }
        }
        return instance;
    }



    public MemoryCache() {
        int maxSize = (int) (Runtime.getRuntime().freeMemory() / 1024 / 8);
        mLruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //一张图片的大小
                return value.getRowBytes() * value.getHeight();
            }
        };

        activityCache = new HashMap<>();

    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        if (bitmap != null){
            mLruCache.put(request.getUriMD5(), bitmap);

            //和生命周期绑定
            activityCache.put(request.getUriMD5(),request.getContext().hashCode());

        }
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        return mLruCache.get(request.getUriMD5());
    }

    @Override
    public void remove(BitmapRequest request) {
        mLruCache.remove(request.getUriMD5());
    }

    public void remove(int acvityCode) {
      //  mLruCache.remove(request.getUriMD5());
        //移除当前 activity 所有  bitmap

        List<String> tempUriMdeList = new ArrayList<>();

        for (String uriMd5 : activityCache.keySet()){
            if (activityCache.get(uriMd5).intValue() == acvityCode){
               // activityCache.remove(uriMd5); 不能这么做
                tempUriMdeList.add(uriMd5);
            }
        }


        //移除
        for (String uriMd5: tempUriMdeList){
            activityCache.remove(uriMd5);
            Bitmap bitmap  = mLruCache.get(uriMd5);
            if (bitmap != null && !bitmap.isRecycled()){

                bitmap.recycle();
            }
            mLruCache.remove(uriMd5);
            bitmap = null;
        }


        if (!tempUriMdeList.isEmpty()){
            System.gc();
        }

    }


}