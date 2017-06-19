package com.dianwoba.cn.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by leizhen on 2017/5/16 0016.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
@Service
public class RedisService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        RedisService redisService = new RedisService();
        List orderList = redisService.getRiderOrderList("44");
        System.out.println(orderList);
    }


    private ShardedJedis connectJedis() {
        // 连接远程 RedisService 服务
        String masterNames = "sharddev6389";
        Set<String> sentinels = new HashSet<String>();
        sentinels.add("192.168.11.29:26002");
        sentinels.add("192.168.11.32:26002");
        sentinels.add("192.168.11.20:26002");
        String password = "wIvJt@_redis";
        SharedJedisSentinelPool sentinelPool = new SharedJedisSentinelPool(masterNames, sentinels, password);

        ShardedJedis jedis = null;
        try {
            jedis = sentinelPool.getResource();
            //            System.out.println(jedis.set("test", "value12123132"));
            //            System.out.println(jedis.get("test"));
        } catch (Exception e) {
            if (null != jedis) {
                jedis.close();
                logger.error("连接失败，结束连接");
            }
        }
        return jedis;
    }

    public List<String> getRiderOrderList(String riderId) {
        ShardedJedis jedis = connectJedis();
        List<String> orderList = new ArrayList<String>();
        try {
            orderList = new ArrayList<String>(
                    jedis.zrange("pirate:r:rol:n:" + riderId, 0, -1));
        } finally {
            if (null != jedis) {
                jedis.close();
                logger.debug("结束连接");
            }
        }
        return orderList;
    }


}



