package com.dianwoba.cn;

import com.dianwoba.cn.service.RedisServiceImpl;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by leizhen on 2017/5/15 0015.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class RedisTest {

    private static ConfigurableApplicationContext context;

    RedisServiceImpl service;

    @Test
    public void testSave(){
        context = new ClassPathXmlApplicationContext();
    }
}
