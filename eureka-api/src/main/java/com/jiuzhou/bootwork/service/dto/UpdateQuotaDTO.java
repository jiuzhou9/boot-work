package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/19
 */
@Data
public class UpdateQuotaDTO {

    /**
     * 请求者的信息
     */
    private String key;

    /**
     * 请求资源编码
     */
    private String resourceCode;

    /**
     * 额度，额度为负数是减少额度，额度为正数是增加额度
     */
    private Long updateQuota;
}
