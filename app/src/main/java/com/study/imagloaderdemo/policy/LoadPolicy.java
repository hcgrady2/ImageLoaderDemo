package com.study.imagloaderdemo.policy;

import com.study.imagloaderdemo.request.BitmapRequest;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */

public interface LoadPolicy {
    /**
     * 两个BItmapRequest进行优先级比较
     * @param request1
     * @param request2
     * @return
     */
    int compareto(BitmapRequest request1, BitmapRequest request2);
}