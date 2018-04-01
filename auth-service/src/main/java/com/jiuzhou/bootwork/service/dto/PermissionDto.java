package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/04/01
 */
@Data
public class PermissionDto {

    private String serverResource;

    private Long resourceId;

    private List<String> roleNames;

    private String methodType;
}
