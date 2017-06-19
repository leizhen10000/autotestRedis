package com.dianwoba.cn.testng.spring.test;

import com.dianwoba.cn.testng.spring.service.RandomEmailGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Created by leizhen on 2017/6/1.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
@Test
public class TestSpring {

    @Autowired
    RandomEmailGenerator emailGenerator;

    private void testEmailGenerator() {
        String email = emailGenerator.generate();
        System.out.println(email);

//        Assert.assertNotNull(email);
//        Assert.assertEquals(email, "feedback@yiibai.com");
    }

    public static void main(String[]args){
        TestSpring t = new TestSpring();
        t.testEmailGenerator();
    }
}
