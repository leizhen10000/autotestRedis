package com.dianwoba.cn.service;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * Created by leizhen on 2017/5/12 0012.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class GetRedisKey {
    public static void main(String[] args) {

        // 连接本地的 RedisService 服务
        Jedis localJedis = new Jedis("localhost");
        System.out.println("Connection to server successfully");

        // 查看服务是否进行
        System.out.println("\n\n\n\nServer is running: " + localJedis.ping());

        System.out.println("The key of name is " + localJedis.get("name"));

        localJedis.set("age", "12");
        System.out.println("The value of age is " + localJedis.get("age"));

        // RedisService List
        localJedis.lpush("tutorial-list", "RedisService");
        localJedis.lpush("tutorial-list", "Mongodb");
        localJedis.lpush("tutorial-list", "Mysql");
        List<String> list = localJedis.lrange("tutorial-list", 0, 5);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Stored string in redis:: " + list.get(i));
        }

        // RedisService Keys
        Set<String> set = localJedis.keys("*");
        for (String str : set) {
            System.out.println("List of stored keys : " + str);
        }
    }


}
