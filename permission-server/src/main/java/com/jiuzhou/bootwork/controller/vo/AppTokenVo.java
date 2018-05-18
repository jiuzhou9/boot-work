package com.jiuzhou.bootwork.controller.vo;

import lombok.Data;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/30
 */
@Data
public class AppTokenVo {

    private String appToken;
    private String code;
    private String userToken;
    private String userName;
    private String appName;
    private String serverResource;
    private String method;
}
