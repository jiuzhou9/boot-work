package com.jiuzhou.bootwork.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis Client Tool
 *
 */
@Component
@Configuration
@Slf4j
public class CacheClient {

    @Value("${jedis.pool.host}")
    private String host;

    @Value("${jedis.pool.port}")
    private int port;

    @Value("${jedis.pool.password}")
    private String password;

    @Value("${redis.preKey}")
    private String preKey;

    private JedisPool pool;

    private boolean redisEnable = true;

    @PostConstruct
    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(50);
        config.setMaxTotal(300);
        config.setMaxWaitMillis(1000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        log.error("host is :" + host + "; port is : " + port);
        pool = new JedisPool(config, host, Integer.valueOf(port), 3000, password);
        if (StringUtils.isEmpty(password)){
            pool = new JedisPool(config, host, Integer.valueOf(port), 3000);
        }
        Jedis resource = pool.getResource();
        resource.close();
        log.info("redis pool init success");
    }

    @PreDestroy
    public void destory() {
        pool.close();
    }

    protected Jedis getResource() {
        return pool.getResource();
    }

    protected void returnResource(Jedis jedis) {
        jedis.close();
    }

    public <T> void innerPut(String key, T value) {
        innerPut(key, value, 7200);
    }

    public <T> void innerPut(String key, T value, int num, TimeUnit unit) {
        int seconds = getSeconds(num, unit);
        innerPut(key, value, seconds);
    }

    public <T> void innerPut(String key, T value, int seconds) {
        if (redisEnable == false) {
            return;
        }
        if (value == null) {
            return;
        }
        byte[] data = stringToByte(JSON.toJSONString(value));
        Jedis resource = getResource();
        try {
            byte[] keyBytes = key.getBytes();
            resource.set(keyBytes, data);
            resource.expire(keyBytes, seconds);
        } finally {
            resource.close();
        }
    }

    public String simpleGet(String key) {
        if (redisEnable == false) {
            return null;
        }
        String value = null;
        Jedis resource = getResource();
        try {
            byte[] data = resource.get(stringToByte(key));
            if (data == null) {
                return null;
            }
            value = byteToString(data);
        } finally {
            resource.close();
        }
        return value;
    }

    public Set<String> keys(String pattern) {
        Jedis resource = getResource();
        return resource.keys(pattern);
    }

    private String byteToString(byte[] data) {
        try {
            return new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public void simplePut(String key, String value, int seconds) {
        if (redisEnable == false) {
            return;
        }
        if (value == null) {
            return;
        }

        byte[] data = stringToByte(value);
        Jedis resource = getResource();
        try {
            byte[] keyBytes = stringToByte(key);
            resource.set(keyBytes, data);
            resource.expire(keyBytes, seconds);
        } finally {
            resource.close();
        }
    }

    private byte[] stringToByte(String value) {
        try {
            return value.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public <T> T innerGet(String key, Class<T> clazz) {
        if (redisEnable == false) {
            return null;
        }
        Jedis resource = getResource();
        try {
            byte[] data = resource.get(key.getBytes());
            if (data == null) {
                return null;
            }
            T value = JSONObject.parseObject(byteToString(data), clazz);
            return value;
        } finally {
            resource.close();
        }
    }

    public void innerRemove(String key) {
        if (redisEnable == false) {
            return;
        }
        Jedis resource = getResource();
        resource.del(key.getBytes());
        resource.close();
    }

    private int getSeconds(int num, TimeUnit unit) {
        if (TimeUnit.SECONDS.equals(unit)) {
            return num;
        } else if (TimeUnit.HOURS.equals(unit)) {
            return num * 3600;
        } else if (TimeUnit.MINUTES.equals(unit)) {
            return num * 60;
        } else if (TimeUnit.DAYS.equals(unit)) {
            return num * 24 * 3600;
        } else if (TimeUnit.MICROSECONDS.equals(unit)) {
            return (int) TimeUnit.MICROSECONDS.toSeconds(num);
        } else if (TimeUnit.MILLISECONDS.equals(unit)) {
            return (int) TimeUnit.MILLISECONDS.toSeconds(num);
        } else if (TimeUnit.NANOSECONDS.equals(unit)) {
            return (int) TimeUnit.NANOSECONDS.toSeconds(num);
        }
        return 1800;
    }

    /**
     * 永不过期
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void putForever(String key, T value){
        if (redisEnable == false) {
            return;
        }
        if (value == null) {
            return;
        }
        byte[] data = stringToByte(JSON.toJSONString(value));
        Jedis resource = getResource();
        try {
            byte[] keyBytes = key.getBytes();
            resource.set(keyBytes, data);
        } finally {
            resource.close();
        }
    }

    public Long incrBy(String key, long integer) {
        Jedis resource = getResource();
        return resource.incrBy(key, integer);
    }

    public void deleteAllByPattern(String patternKey, List<String> ignoreKeys){
        Jedis resource = getResource();
        Set<String> keys = resource.keys(patternKey + "*");
        if (!CollectionUtils.isEmpty(ignoreKeys)){
            keys.removeAll(ignoreKeys);
        }

        for (String redisKey : keys) {
            resource.del(redisKey);
        }
    }

    public String getPreKey() {
        return preKey;
    }
}
