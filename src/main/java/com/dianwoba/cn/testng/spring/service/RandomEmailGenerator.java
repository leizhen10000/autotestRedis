package com.dianwoba.cn.testng.spring.service;

import com.dianwoba.cn.testng.spring.jo.EmailGenerator;
import org.springframework.stereotype.Service;

/**
 * Created by leizhen on 2017/6/1.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
@Service
public class RandomEmailGenerator implements EmailGenerator {

    public String generate() {
        return "feedback@yiibai.com";
    }

}
