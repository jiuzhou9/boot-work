package com.jiuzhou.bootwork.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/31
 */
@Data
public class AppTokenVo {
    @ApiModelProperty(value = "APP 令牌")
    private String appToken;
    @ApiModelProperty(value = "APP code")
    private String code;
    @ApiModelProperty(value = "用户令牌")
    private String userToken;
    @ApiModelProperty(value = "user name")
    private String userName;
    @ApiModelProperty(value = "app name")
    private String appName;
}
