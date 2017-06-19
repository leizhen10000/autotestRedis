package com.dianwoba.cn.testng.depend;

import org.testng.annotations.Test;

/**
 * Created by leizhen on 2017/5/31.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
@Test(groups = "deploy")
public class TestServer {
    @Test
    public void deployServer() {
        System.out.println("Deploying Server ... ");
    }

    @Test(dependsOnMethods = "deployServer")
    public void deployBackUpServer() {
        System.out.println("Deploying Backup Server ... ");
    }
}
