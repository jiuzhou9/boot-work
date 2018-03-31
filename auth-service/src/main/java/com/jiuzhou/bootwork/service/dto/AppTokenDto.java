package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/30
 */
@Data
public class AppTokenDto {

    private String appToken;
    private String code;
    private String appName;
    private String userName;
}
