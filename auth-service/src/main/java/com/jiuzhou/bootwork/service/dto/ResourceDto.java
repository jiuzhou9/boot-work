package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/23
 */
@Data
public class ResourceDto {
    private Long id;

    private String name;

    private String description;

    private String url;

    private String type;

    private Long serverId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean available;
}