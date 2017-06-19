package com.dianwoba.cn;

import org.testng.annotations.Test;

/**
 * Created by leizhen on 2017/5/31.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class TestTimeout {

    @Test(timeOut = 500)
    public void testThisShouldPass() throws InterruptedException {
        Thread.sleep(400);
    }

    @Test(timeOut = 100)
    public void testThisShouldFail() throws InterruptedException {
        Thread.sleep(150);
    }
}
