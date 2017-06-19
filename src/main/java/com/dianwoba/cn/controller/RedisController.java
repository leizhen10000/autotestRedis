package com.dianwoba.cn.controller;

import com.dianwoba.cn.domain.User;
import com.dianwoba.cn.dto.RedisDto;
import com.dianwoba.cn.redis.RedisService;
import com.dianwoba.cn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by leizhen on 2017/5/11 0011.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;


    @RequestMapping(value = "/getKey/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public RedisDto getValueByKey(@PathVariable String orderId) {
        RedisDto redisDto = new RedisDto();
        List<String> orderIdList = redisService.getRiderOrderList(orderId);
        redisDto.setOrderIdList(orderIdList);
        return redisDto;
    }

    @RequestMapping("/findUser")
    public ModelAndView findUser() {
        ModelAndView modelAndView = new ModelAndView();

        User user = userService.selectUserById(1);
        modelAndView.addObject("users", user);
        modelAndView.setViewName("findUser");

        return modelAndView;
    }


}
