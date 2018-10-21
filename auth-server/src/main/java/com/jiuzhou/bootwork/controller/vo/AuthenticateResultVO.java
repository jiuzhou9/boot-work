package com.jiuzhou.bootwork.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/20
 *
 * 请求认证结果
 */
@Data
public class AuthenticateResultVO {

    @ApiModelProperty(value = "1：当前请求需要回调更新额度回调，2：当前请求不需要更新额度")
    private Integer callback;

    @ApiModelProperty(value = "key")
    private String key;

    @ApiModelProperty(value = "资源code")
    private String resourceCode;

    @ApiModelProperty(value = "公司ID")
    private Integer companyId;

    @ApiModelProperty(value = "请求唯一标识ID")
    private String requestId;

    @ApiModelProperty(value = "公司code")
    private String companyCode;

    /**
     * 公司name
     */
    @ApiModelProperty(value = "公司名称")
    private String name;

    @ApiModelProperty(value = "公司业务编码")
    private String businessCode;
}
