package com.jiuzhou.bootwork.beans;

import lombok.Data;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/06/26
 */
@Data
public class MenuListDTO {
    private Integer id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 资源路径
     */
    private String uri;
    /**
     * 子菜单
     */
    private List<MenuListDTO> subMenus;
}
