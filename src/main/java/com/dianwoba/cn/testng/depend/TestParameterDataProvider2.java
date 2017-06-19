package com.dianwoba.cn.testng.depend;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leizhen on 2017/5/31.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class TestParameterDataProvider2 {

    @Test(dataProvider = "dbconfig")
    public void testConnection(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(" [Key] : " + entry.getKey() + " \n[Value] :" + entry.getValue());
        }
    }

    @DataProvider(name = "dbconfig")
    public Object[][] provideDbConfig() {
        Map<String, String> map = readDbConfig();
        return new Object[][]{{map}};
    }

    public Map<String, String> readDbConfig() {

        Map<String, String> map = new HashMap<String, String>();

        try {
            map.put("jdbc.driver", "driverclass");
            map.put("jdbc.url", "url");
            map.put("jdbc.username", "username");
            map.put("jdbc.password", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
