package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/20
 */
@Data
public class ApiRequestDTO {

    private String key;

    private String keyToken;

    private Map<String, Object> params;

    private String sign;

    private String nonce;

    private String apiPath;

    private Long reqTimestamp;

    private String requestMethod;

    private String body;

}
