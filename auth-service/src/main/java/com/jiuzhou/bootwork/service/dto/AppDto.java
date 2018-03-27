package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppDto {
    private Long id;

    private String name;

    private String code;

    private Long userId;

    private Boolean available;

    private String secret;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}