package com.jiuzhou.bootwork.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/18
 */
@Data
public class ApiRequestVO {

    @ApiModelProperty(value = "key")
    private String key;

    @ApiModelProperty(value = "key 令牌")
    private String keyToken;

    @ApiModelProperty(value = "api 请求的 url 中的参数")
    private Map<String, Object> params;

    @ApiModelProperty(value = "请求签名")
    private String sign;

    @ApiModelProperty(value = "客户端唯一标识，由客户端自己生成")
    private String nonce;

    @ApiModelProperty(value = "api path请求路径")
    private String apiPath;

    @ApiModelProperty(value = "请求时间戳")
    private Long reqTimestamp;

    @ApiModelProperty(value = "api 请求方式，如：GET、PUT等")
    private String requestMethod;

    @ApiModelProperty(value = "post请求中的body参数体")
    private String body;

}
