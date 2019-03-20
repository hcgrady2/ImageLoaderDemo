package com.study.imagloaderdemo.request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.study.imagloaderdemo.cache.DoubleCache;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingDeque;

/**
 * Created by hcw on 2019/3/13.
 * Copyright©hcw.All rights reserved.
 */

//相对于消费者，去消费掉请求，肯定是耗时的
public class BitmapDispatcher extends Thread {

    private static final String TAG = "GlideDemo";
    private Handler handler = new Handler(Looper.getMainLooper());

    DoubleCache doubleCache = DoubleCache.getInstance();

    private BlockingDeque blockingDeque;
    public BitmapDispatcher(BlockingDeque<BitmapRequest> requests){
        this.blockingDeque = requests;
    }

    @Override
    public void run() {
        while (!isInterrupted()){
            //没有任务会阻塞、
            try {
                BitmapRequest request = (BitmapRequest) blockingDeque.take();
                //取到任务，先显示 loading
                showLodingImage(request);
                //加载图片
                Bitmap bitmap = findBitmap(request);
                //刷新 ui
                deliveryUIThread(request,bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    private void deliveryUIThread(final BitmapRequest request,final Bitmap bitmap){

        // 自线程 ----> UI 线程

        handler.post(new Runnable() {
            @Override
            public void run() {
                ImageView imageView = request.getImageView();
                if (imageView != null && bitmap != null && imageView.getTag().equals(request.getUriMD5())){
                    imageView.setImageBitmap(bitmap);
                }
            }
        });

        //监听网络请求是否成功
        if ( request.getListener() != null){
            if (bitmap != null){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        request.getListener().onResouceReady(bitmap);
                    }
                });
            }else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        request.getListener().onException();
                    }
                });
            }
        }

    }

    private Bitmap findBitmap(BitmapRequest request) {
        //缓存
        Bitmap  bitmap = doubleCache.get(request);
        if (bitmap != null){
            return bitmap;
        }
        //网络下载
        bitmap = downloadImage(request.getUri());
        if (bitmap != null){
            doubleCache.put(request,bitmap);
        }
        return bitmap;
    }



    private Bitmap downloadImage(String uri) {
        FileOutputStream os = null;
        InputStream is = null;
        Bitmap bitmap = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (is != null){
                    is.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                if (os != null){
                    os.close();
                }
            }catch (IOException e){

            }
        }
        return bitmap;
    }


    private void showLodingImage(BitmapRequest request){
        if (request.getLoadingResId() > 0){
            final ImageView imageView = request.getImageView();
            final int resId = request.getLoadingResId();
            if (imageView != null){
                handler.post(new Runnable(){
                    @Override
                    public void run() {
                        imageView.setImageResource(resId);
                    }
                });
            }
        }
    }



}
