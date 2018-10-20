package com.jiuzhou.bootwork.config;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存基类，继承基类并指定缓存对象的类型泛型
 */
public abstract class CacheManager<T> {

    @Resource
    protected CacheClient cacheClient;

    protected String MIDDLE_KEY;

    protected abstract String getKey(T value);

    protected abstract String getKey(String key);

    protected abstract Class<T> getDTOClass();

    public CacheManager(String MIDDLE_KEY) {
        this.MIDDLE_KEY = MIDDLE_KEY;
    }

    /**
     * 重组key前缀
     * @return
     */
    public String getPreAndMiddleKey() {
        String preKey = cacheClient.getPreKey();
        return preKey + this.MIDDLE_KEY;
    }

    public void put(T value) {
        String key = getKey(value);
        cacheClient.innerPut(key, value);
    }

    public void put(String key, T value) {
        cacheClient.innerPut(key, value);
    }

    public void put(String key, T value, int num, TimeUnit unit) {
        cacheClient.innerPut(key, value, num, unit);
    }

    public void put(T value, int seconds) {
        String key = getKey(value);
        cacheClient.innerPut(key, value, seconds);
    }

    public T get(String key) {
        try {
            key = getKey(key);
            T value = cacheClient.innerGet(key, getDTOClass());
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public void remove(String key) {
        key = getKey(key);
        if (key != null) {
            cacheClient.innerRemove(key);
        }
    }

    protected Set<String> getKeys(String pattern) {
        return cacheClient.keys(pattern);
    }

    /**
     * 永不过期
     * @param value
     */
    public void putForever(T value) {
        String key = getKey(value);
        cacheClient.putForever(key, value);
    }

    /**
     * 指定的key做加法运算
     * @param value
     * @param integer
     */
    public Long incrBy(T value, final long integer){
        String key = getKey(value);
        return cacheClient.incrBy(key, integer);
    }

}
