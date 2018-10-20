/*
 * Copyright (c) 2017-2018, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/17
 */
@Data
public class AccesskeyDTO {

    private Integer id;

    private String key;

    private String name;

    private String companyCode;

    private String userCode;

    private String secret;

    private String privateSecret;

    private String constraint;

    private LocalDateTime expireTime;

    private Boolean active;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
