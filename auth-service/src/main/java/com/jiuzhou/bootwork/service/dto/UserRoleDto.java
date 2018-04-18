package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserRoleDto {
    private Long id;

    private Long userId;

    private Long roleId;

    private Boolean available;

    private Long remainder;

    private Date startTime;

    private Date endTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;

}