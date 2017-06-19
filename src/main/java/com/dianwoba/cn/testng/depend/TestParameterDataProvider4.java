package com.dianwoba.cn.testng.depend;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by leizhen on 2017/6/1.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class TestParameterDataProvider4 {

    @Test(dataProvider = "dataProvider", groups = "groupA")
    public void test1(int number) {
        Assert.assertEquals(number, 1);
    }

    @Test(dataProvider = "dataProvider", groups = "groupB")
    public void test2(int number) {
        Assert.assertEquals(number, 2);
    }

    @DataProvider(name = "dataProvider")
    public Object[][] dataPro(ITestContext context) {

        Object[][] result = null;

        for (String group : context.getIncludedGroups()) {
            System.out.println("group: " + group);
            if ("groupA".equals(group)) {
                result = new Object[][]{{1}};
                break;
            }
        }
        if (null == result) {
            result = new Object[][]{{2}};
        }
        return result;
    }

}
