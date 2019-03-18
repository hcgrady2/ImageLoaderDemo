package com.study.imagloaderdemo.request;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

public class RequestQueue {
    /**
     * 优先级阻塞式队列
     * 多线程共享
     * 生产效率和消费者效率差太远
     * 优先级高的队列先被消费
     * 每一个产品都有编号
     */

    private BlockingQueue<BitmapRequest> mRequestsQueue = new PriorityBlockingQueue<>();

    /**
     * 转发器数量
     */
    private int threadcount;
    //一组转发器
    private RequestDispatcher[] mDispatcher;

    private AtomicInteger i = new AtomicInteger(0);

    public RequestQueue(int threadcount) {
        this.threadcount = threadcount;
    }

    /**
     * 添加请求对象
     *
     * @param request
     */
    public void addRequest(BitmapRequest request) {
        //判断请求队列是否包含请求
        if (!mRequestsQueue.contains(request)) {
            //给请求进行编号
         //   request.setSerialNo(i.incrementAndGet());
            mRequestsQueue.add(request);
        } else {
            Log.w("", "请求已经存在  编号：" );
        }
    }

    /**
     * 开启请求
     */
    public void start() {
        //先停止，在开启
        stop();
        startDispatchers();


    }

    private void startDispatchers() {
        mDispatcher=new RequestDispatcher[threadcount];
        for (int i=0;i<threadcount;i++){
            RequestDispatcher dispatcher=new RequestDispatcher(mRequestsQueue);
            mDispatcher[i]=dispatcher;
            mDispatcher[i].start();
        }

    }

    /**
     * 停止请求
     */
    public void stop() {

    }


}