package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/16
 */
@Data
public class UserLoginDTO {
    /**
     * 用户名/邮箱/手机号
     */
    private String userStr;

    /**
     * 密码
     */
    private String password;
}
