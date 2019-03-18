package com.study.imagloaderdemo.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.study.imagloaderdemo.request.BitmapRequest;
import com.study.imagloaderdemo.utils.BitmapDecoder;
import com.study.imagloaderdemo.utils.ImageviewHelper;

import java.io.File;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

public class LocalLoader extends AbstarctLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        //得到本地图片的路径
        final String path = Uri.parse(request.getUri()).getPath();

        File file = new File(path);
        if (!file.exists()) {

            return null;
        }
        BitmapDecoder decoder = new BitmapDecoder() {
            @Override
            protected Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(path, options);
            }
        };


        return decoder.decoderBitmap(ImageviewHelper.getImageViewWidth(request.getImageView()),
                ImageviewHelper.getImageViewHeight(request.getImageView()));
    }
}