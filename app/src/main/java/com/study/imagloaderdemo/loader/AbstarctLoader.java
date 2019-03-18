package com.study.imagloaderdemo.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.study.imagloaderdemo.cache.BitmapCache;
import com.study.imagloaderdemo.config.DisplayConfig;
import com.study.imagloaderdemo.request.BitmapRequest;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

public abstract class AbstarctLoader implements Loader {
    //拿到用户自定义配置的缓存策略
    private BitmapCache bitmapCache = SimpleImageLoader.getInstance().getConfig().getBitmapCache();
    //拿到显示配置
    private DisplayConfig displayConfig = SimpleImageLoader.getInstance().getConfig().getDisplayconfig();

    @Override
    public void loadImage(BitmapRequest request) {
        //从缓存中取到bitmap
        Bitmap bitmap = bitmapCache.get(request);
        if (bitmap == null) {
            //显示默认加载图片
            showLoadingImage(request);
            //开始真正加载图片
            bitmap = onLoad(request);
            //缓存图片
            cacheBitmap(request, bitmap);
        }

        deliveryToUIThread(request,bitmap);
    }
    /**
     * 交给主线程显示
     * @param request
     * @param bitmap
     */
    protected void deliveryToUIThread(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        if(imageView!=null)
        {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    updateImageView(request, bitmap);
                }

            });
        }

    }

    private void updateImageView(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        //加载正常  防止图片错位
        if(bitmap != null && imageView.getTag().equals(request.getUri())){
            imageView.setImageBitmap(bitmap);
        }

  /*
        //有可能加载失败
        if(bitmap == null && request.getDisplayConfig()!=null&&request.getDisplayConfig().failImage!=-1){
            imageView.setImageResource(displayConfig.failImage);
        }
        //监听
        //回调 给圆角图片  特殊图片进行扩展
        if(request.imageListener != null){
            request.imageListener.onComplete(imageView, bitmap, request.getUri());
        }
        */


    }

    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        if (request != null && bitmap != null) {
            synchronized (AbstarctLoader.class) {
                bitmapCache.put(request, bitmap);
            }
        }
    }

    //抽象加载策略，因为加载网络图片和本地图片有差异
    protected abstract Bitmap onLoad(BitmapRequest request);

    private void showLoadingImage(BitmapRequest request) {
        //指定了，显示配置
        if (hasLoadingPlaceHolder()) {
            final ImageView imageView = request.getImageView();
            if (imageView != null) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(displayConfig.loadingImage);
                    }
                });
            }
        }
    }

    protected boolean hasLoadingPlaceHolder() {
        return (displayConfig != null && displayConfig.loadingImage > 0);
    }
}