package com.study.imagloaderdemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.study.imagloaderdemo.R;
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
        Glide.with(this).load("http://pic22.nipic.com/20120714/9622064_105642209176_2.jpg")
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
     //   imageView.setImageResource(R.drawable.length);
    }

    public void loadMore(){
        startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}

