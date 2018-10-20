package com.jiuzhou.bootwork.cache;

import com.jiuzhou.bootwork.cache.cdto.ResourceCacheDTO;
import com.jiuzhou.bootwork.cache.config.CacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/13
 *
 * 资源缓存
 */
@Component
@Slf4j
public class ResourceCacheManager extends CacheManager<ResourceCacheDTO> {

    public static final String MIDDLE_KEY = "RESOURCE_";

    @Override
    protected String getKey(ResourceCacheDTO dto) {
        return getKey(dto.getServerName());
    }

    @Override
    protected String getKey(String endKey) {
        return getPreAndMiddleKey() + endKey;
    }

    @Override
    protected Class<ResourceCacheDTO> getDTOClass() {
        return ResourceCacheDTO.class;
    }

    public ResourceCacheManager() {
        super(MIDDLE_KEY);
    }

}
