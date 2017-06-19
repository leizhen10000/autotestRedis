package com.dianwoba.cn.testng.depend;

import org.testng.annotations.Test;

/**
 * Created by leizhen on 2017/5/31.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class TestDatabase {

    @Test(groups = "db", dependsOnGroups = "deploy")
    public void initDB() {
        System.out.println("This is initDB");
    }

    @Test(groups = "db", dependsOnMethods = "initDB")
    public void testConnection() {
        System.out.println("This is testConnection()");
    }
}
