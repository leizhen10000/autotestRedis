package com.dianwoba.cn.testng.depend;

import org.testng.annotations.Test;

/**
 * Created by leizhen on 2017/5/31.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class TestApp {
    @Test(dependsOnGroups = {"deploy", "db"})
    public void method1() {
        System.out.println("This is method 1");
    }

    @Test(dependsOnMethods = "method1")
    public void method2() {
        System.out.println("This is method 2");
    }
}
