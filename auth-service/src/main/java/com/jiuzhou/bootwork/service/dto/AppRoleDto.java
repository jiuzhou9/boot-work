package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppRoleDto {
    private Long appId;

    private Long roleId;

    private Boolean available;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}