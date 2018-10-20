package com.jiuzhou.bootwork.cache.cdto;

import com.jiuzhou.bootwork.service.dto.QuotaDTO;
import lombok.Data;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/15
 */
@Data
public class QuotaCacheDTO {

    /**
     * Redis缓存键
     * QuotaDTO 中的 key + "_" + resourceCode 构成键
     */
    private String quotaKey;

    private QuotaDTO quotaDTO;
}
