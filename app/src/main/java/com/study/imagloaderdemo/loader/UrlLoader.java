package com.study.imagloaderdemo.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.study.imagloaderdemo.request.BitmapRequest;
import com.study.imagloaderdemo.utils.BitmapDecoder;
import com.study.imagloaderdemo.utils.ImageviewHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

public class UrlLoader extends AbstarctLoader {
    @Override
    protected Bitmap onLoad(final BitmapRequest request) {
        //先下载  后读取
        downloadImgByUrl(request.getUri(), getCache(request.getUriMD5()));


        //解码图片
        BitmapDecoder bitmapDecoder = new BitmapDecoder() {
            @Override
            protected Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                //bitmap 对象值为空，因为读取的只是宽高
                return BitmapFactory.decodeFile(getCache(request.getUriMD5()).getAbsolutePath(), options);
            }
        };
        return bitmapDecoder.decoderBitmap(ImageviewHelper.getImageViewWidth(request.getImageView()),
                ImageviewHelper.getImageViewHeight(request.getImageView()));


    }


    public static boolean downloadImgByUrl(String urlStr, File file) {
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            is = conn.getInputStream();
            fos = new FileOutputStream(file);
            byte[] buf = new byte[512];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }

            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            }
        }

        return false;

    }

    private File getCache(String unipue) {
        File file = new File(Environment.getExternalStorageDirectory(), "ImageLoader");
        if (!file.exists()) {
            file.mkdir();
        }
        return new File(file, unipue);
    }
}