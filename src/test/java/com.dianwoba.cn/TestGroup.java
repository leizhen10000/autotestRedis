package com.dianwoba.cn;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

/**
 * Created by leizhen on 2017/5/31.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class TestGroup {

    @BeforeGroups("database")
    public void setupDB(){
        System.out.println("setupDB");
    }

    @AfterGroups("database")
    public void cleanDB() {
        System.out.println("cleanDB");
    }

    @Test(groups = "selenium-test")
    public void runSelenium(){
        System.out.println("runSelenium()");
    }

    @Test(groups = "selenium-test")
    public void runSelenium1(){
        System.out.println("runSelenium1()");
    }

    @Test(groups = "database")
    public void testConnectionOracle(){
        System.out.println("testConnectionOracle()");
    }

    @Test(groups = "database")
    public void testConnectionMySQL(){
        System.out.println("testConnectMySQL");
    }

    @Test(dependsOnGroups = {"database","selenium-test"})
    public void runFinal(){
        System.out.println("runFinal");
    }
}
