package com.dianwoba.cn.service;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by leizhen on 2017/5/15 0015.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class RedisServiceImpl {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    // 线程池
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            246, 256, 30L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new BasicThreadFactory.Builder().daemon(true).namingPattern("reids-oper-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public void set(final String key, final String value) {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.set(
                        redisTemplate.getStringSerializer().serialize(key),
                        redisTemplate.getStringSerializer().serialize(value));
                logger.debug("save key:" + key + ",value:" + value);
                return null;
            }
        });
    }

    public String get(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] byteKey = redisTemplate.getStringSerializer().serialize(key);
                if (redisConnection.exists(byteKey)) {
                    byte[] byteValue = redisConnection.get(byteKey);
                    String value = redisTemplate.getStringSerializer()
                            .deserialize(byteValue);
                    logger.debug("get key: " + key + ",value: " + value);
                    return value;
                }
                logger.error("value does not exist!,key: " + key);
                return null;
            }
        });
    }

    public void delete(final String key) {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.del(redisTemplate.getStringSerializer()
                        .serialize(key));
                return null;
            }
        });
    }

    /**
     * 线程池并发操作 redis
     *
     * @param keyValue
     */
    public void mulitThreadSaveAndFind(final String keyValue) {
        executor.execute(new Runnable() {
            public void run() {
                try {
                    set(keyValue, keyValue);
                    get(keyValue);
                } catch (Throwable throwable) {
                    logger.error("", throwable);
                }
            }
        });
    }
}
