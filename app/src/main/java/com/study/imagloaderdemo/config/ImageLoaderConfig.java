package com.study.imagloaderdemo.config;

import com.study.imagloaderdemo.cache.BitmapCache;
import com.study.imagloaderdemo.cache.MemoryCache;
import com.study.imagloaderdemo.policy.LoadPolicy;
import com.study.imagloaderdemo.policy.ReversePolicy;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

public class ImageLoaderConfig {

    //缓存策略
    private BitmapCache bitmapCache=new MemoryCache();

    //加载策略
    private LoadPolicy loadPolicy=new ReversePolicy();
    //默认加载线程数
    private int threadcount = Runtime.getRuntime().availableProcessors();
    //显示配置

    private DisplayConfig displayconfig=new DisplayConfig();

    private ImageLoaderConfig() {

    }

    /**
     * 建造者模式
     */
    public static class Builder {
        private ImageLoaderConfig config;

        public Builder() {
            this.config = new ImageLoaderConfig();
        }

        /**
         * 设置缓存策略
         *
         * @param bitmapCache
         * @return
         */
        public Builder setCachePlicy(BitmapCache bitmapCache) {
            this.config.bitmapCache = bitmapCache;

            return this;
        }

        /**
         * 设置加载策略
         *
         * @param loadpolicy
         * @return
         */
        public Builder setLoadpolicy(LoadPolicy loadpolicy) {
            this.config.loadPolicy = loadpolicy;
            return this;
        }

        /**
         * 设置线程数量
         *
         * @param threadCount
         * @return
         */
        public Builder setThreadCount(int threadCount) {
            this.config.threadcount = threadCount;
            return this;
        }

        /***
         * 设置加载过程中的图片
         * @param ResID
         * @return
         */
        public Builder setLoadingImage(int ResID) {
            this.config.displayconfig.loadingImage = ResID;
            return this;
        }

        /**
         * 设置加载失败的图片
         *
         * @param ResID
         * @return
         */
        public Builder setFailImage(int ResID) {
            this.config.displayconfig.failImage = ResID;
            return this;
        }


        public ImageLoaderConfig build(){

            return this.config;
        }




    }

    public BitmapCache getBitmapCache() {
        return bitmapCache;
    }

    public LoadPolicy getLoadPolicy() {
        return loadPolicy;
    }

    public int getThreadcount() {
        return threadcount;
    }

    public DisplayConfig getDisplayconfig() {
        return displayconfig;
    }
    
}
