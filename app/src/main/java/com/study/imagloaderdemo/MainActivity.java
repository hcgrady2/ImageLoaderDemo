package com.study.imagloaderdemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.study.imagloaderdemo.core.Glide;
import com.study.imagloaderdemo.listener.RequestListener;

public class MainActivity extends AppCompatActivity {

    Button one,more;

    LinearLayout main_li;

    //读写权限
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        one = findViewById(R.id.one);
        more = findViewById(R.id.more);

        main_li = findViewById(R.id.main_li);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadOne();
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMore();
            }
        });
    }

    public void loadOne(){
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        main_li.addView(imageView);
        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552740382406&di=aa5514b3a4f472e3c9848fbc7cb9b600&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F730e0cf3d7ca7bcb051bd704b0096b63f624a8bc.jpg")
        .loading(R.drawable.ic_bubble_24dp).listener(new RequestListener() {
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

    public void loadMore(){

    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}

