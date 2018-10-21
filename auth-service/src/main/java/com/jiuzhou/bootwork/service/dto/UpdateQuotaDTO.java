package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/19
 */
@Data
public class UpdateQuotaDTO {

    /**
     * 指定key
     */
    private String key;

    /**
     * 指定资源code
     */
    private String resourceCode;

    /**
     * 更新额度，正数就是增加，负数就是减少
     */
    private Long updateQuota;
}
