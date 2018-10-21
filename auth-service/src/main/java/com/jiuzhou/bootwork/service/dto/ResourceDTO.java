package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/15
 */
@Data
public class ResourceDTO {
    private Integer id;

    private String resourceCode;

    private String name;

    private String uri;

    private String type;

    private String action;

    private String comments;

    private Boolean active;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
