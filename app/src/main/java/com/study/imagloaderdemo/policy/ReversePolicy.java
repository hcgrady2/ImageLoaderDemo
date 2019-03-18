package com.study.imagloaderdemo.policy;

import com.study.imagloaderdemo.request.BitmapRequest;

/**
 * Created by hcw on 2019/3/16.
 * Copyright©hcw.All rights reserved.
 */


public class ReversePolicy implements  LoadPolicy {
    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
      //  return request2.getSerialNo()-request1.getSerialNo();
        return 1;
    }
}