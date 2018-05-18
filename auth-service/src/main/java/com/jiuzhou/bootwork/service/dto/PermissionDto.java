package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/04/01
 */
@Data
public class PermissionDto {

    private String serverResource;

    private Long resourceId;

    private List<String> roleNames;

    private String methodType;
}
