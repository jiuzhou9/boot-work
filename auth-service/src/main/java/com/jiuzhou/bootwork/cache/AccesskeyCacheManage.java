package com.jiuzhou.bootwork.cache;

import com.jiuzhou.bootwork.cache.config.CacheManager;
import com.jiuzhou.bootwork.service.dto.AccesskeyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/14
 */
@Component
@Slf4j
public class AccesskeyCacheManage extends CacheManager<AccesskeyDTO> {

    @Override
    protected String getKey(AccesskeyDTO accesskeyDTO) {
        return getKey(accesskeyDTO.getKey());
    }

    @Override
    protected String getKey(String endKey) {
        return getPreAndMiddleKey() + endKey;
    }

    @Override
    protected Class<AccesskeyDTO> getDTOClass() {
        return AccesskeyDTO.class;
    }

    public AccesskeyCacheManage() {
        super("ACCESSKEY_");
    }
}
