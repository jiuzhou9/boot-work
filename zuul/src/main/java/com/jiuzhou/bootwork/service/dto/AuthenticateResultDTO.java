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

    private Integer callback;

    private String key;

    private String resourceCode;

    private Integer companyId;

    private String requestId;

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
