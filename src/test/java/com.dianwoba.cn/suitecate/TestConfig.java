package com.dianwoba.cn.suitecate;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

/**
 * Created by leizhen on 2017/5/31.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class TestConfig {

    @BeforeSuite
    public void testBeforeSuite() {
        System.out.println("testBeforeSuite(suitecase)");
    }

    @AfterSuite
    public void testAfterSuite() {
        System.out.println("testAfterSuite(suitecase)");
    }

    @BeforeTest
    public void testBeforeTest() {
        System.out.println("testBeforeTest(suitecase)");
    }

    @AfterTest
    public void testAfterTest() {
        System.out.println("testAfterTest(suitecase)");
    }
}
