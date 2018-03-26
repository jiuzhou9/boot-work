package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleDto {
    private Long id;

    private String name;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean available;

}