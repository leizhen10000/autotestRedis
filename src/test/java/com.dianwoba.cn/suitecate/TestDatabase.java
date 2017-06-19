package com.dianwoba.cn.suitecate;

import org.testng.annotations.Test;

/**
 * Created by leizhen on 2017/5/31.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class TestDatabase {

    @Test(groups = "db")
    public void testConnectOracle() {
        System.out.println("testConnectOracle");
    }

    @Test(groups = "db")
    public void testConnectMySQL() {
        System.out.println("testConnectMySQL");
    }

    @Test(groups = "db-nosql")
    public void testConnectMongoDB() {
        System.out.println("testConnectMogoDB");
    }

    @Test(groups = {"db", "brokenTests"})
    public void testConnectMySQL1() {
        System.out.println("testConnectMySQL1");
    }
}
