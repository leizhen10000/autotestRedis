package com.dianwoba.cn.testng.depend;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * Created by leizhen on 2017/6/1.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class TestParameterDataProvider3 {

    @DataProvider(name = "dataProvider")
    public Object[][] dataPro(Method method) {
        Object[][] result = null;
        if (method.getName().equals("test1")) {
            result = new Object[][]{
                    {1, 1}, {200, 200}
            };
        } else if (method.getName().equals("test2")) {
            result = new Object[][]{
                    {"test@gmail.com", "test@gmail.com"},
                    {"test@yahoo.com", "test@yahoo.com"}
            };
        }
        return result;
    }

    @Test(dataProvider = "dataProvider")
    public void test1(int number, int expected) {
        Assert.assertEquals(number, expected);
    }

    @Test(dataProvider = "dataProvider")
    public void test2(String email, String expected) {
        Assert.assertEquals(email, expected);
    }


}
