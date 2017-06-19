package com.dianwoba.cn.service.impl;

import com.dianwoba.cn.dao.UserDao;
import com.dianwoba.cn.domain.User;
import com.dianwoba.cn.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by leizhen on 2017/5/11 0011.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;


    public User selectUserById(Integer userId) {
        return userDao.selectUserById(userId);
    }
}
