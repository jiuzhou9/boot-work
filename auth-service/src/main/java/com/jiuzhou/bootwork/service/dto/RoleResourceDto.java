package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleResourceDto {
    private Long id;

    private Long roleId;

    private Long resourceId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean available;

}