package com.dianwoba.cn.testng.depend;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.sql.Connection;

/**
 * Created by leizhen on 2017/5/31.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class TestParameterXML {

    Connection connection;

    @Test
    @Parameters({"dbconfig", "poolsize"})
    public void createConnection(String dbconfig, int poolsize) {
        System.out.println(dbconfig);
        System.out.println(poolsize);
    }
}
