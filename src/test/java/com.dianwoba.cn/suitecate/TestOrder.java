package com.dianwoba.cn.suitecate;

import org.testng.annotations.Test;

/**
 * Created by leizhen on 2017/5/31.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class TestOrder {

    @Test(groups = {"orderBo","save"})
    public void testMakeOrder(){
        System.out.println("testMakeOrder");
    }

    @Test(groups = {"orderBo","save"})
    public void testMakeEmptyOrder(){
        System.out.println("testMakeEmptyOrder");
    }

    @Test(groups = "orderBo")
    public void testUpdateOrder(){
        System.out.println("testUpdateOrder");
    }

    @Test(groups = "orderBo")
    public void testFindOrder(){
        System.out.println("testFindOrder");
    }
}
