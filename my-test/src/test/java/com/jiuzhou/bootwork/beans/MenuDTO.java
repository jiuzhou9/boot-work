package com.jiuzhou.bootwork.beans;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MenuDTO {

    private Integer id;

    private String menuCode;

    private String name;

    private Integer parentId;

    private String path;

    private Byte grade;

    private Byte sort;

    private String uri;

    private String icon;

    private String comments;

    private Byte active;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<MenuDTO> subMenus;
}