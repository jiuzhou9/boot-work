/*
 * Copyright (c) 2017-2018, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Integer id;

    private String userCode;

    private String username;

    private String password;

    private String surname;

    private String givenname;

    private String nickname;

    private String mobile;

    private String phone;

    private String email;

    private String companyCode;

    private String type;

    private String parentCode;

    private String comments;

    private Boolean active;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}