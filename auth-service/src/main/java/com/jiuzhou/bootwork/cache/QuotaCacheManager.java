package com.jiuzhou.bootwork.cache;

import com.jiuzhou.bootwork.cache.cdto.QuotaCacheDTO;
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
public class QuotaCacheManager extends CacheManager<QuotaCacheDTO> {


    @Override
    protected String getKey(QuotaCacheDTO dto) {
        return getKey(dto.getQuotaKey());
    }

    /**
     * 根据key获取Redis中缓存数据
     * @param endKey
     * @return
     */
    @Override
    protected String getKey(String endKey) {
        return getPreAndMiddleKey() + endKey;
    }

    @Override
    protected Class<QuotaCacheDTO> getDTOClass() {
        return QuotaCacheDTO.class;
    }

    public QuotaCacheManager() {
        super("QUOTA_");
    }
}
