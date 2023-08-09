package org.example;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @author tongwenjin
 * @since 2023/7/17
 */
public class RedissonTest1 {

    public static void main(String[] args) {
        Config config = new Config();
        config
                .useSingleServer()
                .setAddress("redis://172.31.4.12:7000")
                .setPassword("LbCn_jMT8NGp8x2NyL9")
                .setDatabase(14)
        ;
        RedissonClient redissonClient = Redisson.create(config);


        RLock lock = redissonClient.getLock("test-redisson-lock");
        lock.lock(100000 , TimeUnit.SECONDS);
    }
}
