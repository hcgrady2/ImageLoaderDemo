package com.study.imagloaderdemo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.study.imagloaderdemo.R;
import com.study.imagloaderdemo.core.Glide;
import com.study.imagloaderdemo.listener.RequestListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SecondActivity extends AppCompatActivity {

    private int count = 0;
    private Button load,lru_btn;
    private LinearLayout second_li;

    private String Path = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532852517262&di=bcbc367241183c39d6e6c9ea2f879166&imgtype=0&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201409%2F07%2F20140907002919_eCXPM.jpeg";
    private ImageLoader imageLoader;

    private static final String TAG = "LruDemo";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 102:
                    Log.i(TAG, "handleMessage: ");
                    byte[] Picture = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                    imageLoader.addBitmap("bitmap", bitmap);

                    ImageView imageView = new ImageView(SecondActivity.this);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    second_li.addView(imageView);
                    imageView.setImageBitmap(bitmap);


                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        imageLoader = new ImageLoader();

        load = findViewById(R.id.load);

        lru_btn = findViewById(R.id.lru_btn);

        second_li = findViewById(R.id.second_li);

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage();
            }
        });

        lru_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    LruTest();
            }
        });
    }


    private void loadImage(){

         ImageView imageView = new ImageView(this);
         imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
         second_li.addView(imageView);
         Glide.with(SecondActivity.this).load("http://lorempixel.com/1600/900/",count++)
                 .loading(R.drawable.length).listener(new RequestListener() {
             @Override
             public boolean onException() {
                 return false;
             }

             @Override
             public boolean onResouceReady(Bitmap resource) {
                 // 其他的处理，比如圆角
                 return false;
             }
         }).into(imageView);

        }


        private void LruTest(){

            Log.i(TAG, "LruTest: ");
            Bitmap bitmap = getBitmapFromCache();
            if(bitmap != null) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                second_li.addView(imageView);
                imageView.setImageBitmap(bitmap);

            } else {
                getBitmapFromInternet();
            }


        }








    private Bitmap getBitmapFromCache() {
        Log.i(TAG, "getBitmapFromCache: ");
        return imageLoader.getBitmap("bitmap");
    }

    private void getBitmapFromInternet() {
        Log.i(TAG, "getBitmapFromInternet: ");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Path)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] Picture_bt = response.body().bytes();
                Message message = handler.obtainMessage();
                message.obj = Picture_bt;
                message.what = 102  ;
                handler.sendMessage(message);
            }
        });
    }







    public class ImageLoader {

        private LruCache<String, Bitmap> lruCache;

        public ImageLoader() {
            int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
            int cacheSize = maxMemory / 8;
            lruCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                }
            };
        }

        public void addBitmap(String key, Bitmap bitmap) {
            Log.i(TAG, "addBitmap: ");
            if (getBitmap(key) == null) {
                lruCache.put(key, bitmap);
            }
        }

        public Bitmap getBitmap(String key) {
               Log.i(TAG, "getBitmap: ");
                return lruCache.get(key);
        }

    }
}
