package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleDto {
    private Long id;

    private String name;

    @Deprecated
//    private Long times;

    /**
     * 时间段
     */
    private Integer timeSlot;

    /**
     * 付费类型
     */
    private Integer type;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean available;

    /**
     * 用户的这个角色剩余的使用次数
     */
    private Long remainder;

    /**
     * 用户的这个角色截止日期
     */
    private LocalDateTime endTime;

}