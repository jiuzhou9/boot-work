package com.jiuzhou.bootwork.cache;

import com.jiuzhou.bootwork.config.CacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/13
 *
 * 请求标识信息缓存
 */
@Component
@Slf4j
public class RequestCountCacheManager extends CacheManager<Long> {


    @Override
    protected String getKey(Long count) {
        return getKey("");
    }

    @Override
    public String getKey(String endKey) {
        return getPreAndMiddleKey();
    }

    @Override
    protected Class<Long> getDTOClass() {
        return Long.class;
    }

    public Long getRedisRequestCount(){
        Long count = 1L;
        Set<String> keys = getKeys(getPreAndMiddleKey());

        if (CollectionUtils.isEmpty(keys)){
            putForever(count);
            return count;
        }else {
            return incrBy(count, 1);
        }
    }

    public RequestCountCacheManager() {
        super("REQUEST_COUNT");
    }
}
