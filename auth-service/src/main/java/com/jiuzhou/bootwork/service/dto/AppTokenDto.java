package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/30
 */
@Data
public class AppTokenDto {

    private String appToken;
    private String code;
    private String appName;
    private String userName;
    private String serverResource;
    private String methodType;
}
