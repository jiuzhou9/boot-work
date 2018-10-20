package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/20
 *
 * 请求认证结果
 */
@Data
public class AuthenticateResultDTO {
    /**
     * 1：当前请求需要回调更新额度回调，2：当前请求不需要更新额度
     */
    private Integer callback;

    private String key;

    private String resourceCode;

    private Integer companyId;

    /**
     * 请求唯一标识ID
     */
    private String requestId;

    /**
     * 公司code
     */
    private String companyCode;

    /**
     * 公司name
     */
    private String name;

    /**
     * 公司业务编码
     */
    private String businessCode;
}
