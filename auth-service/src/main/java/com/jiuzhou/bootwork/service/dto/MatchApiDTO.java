package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/13
 *
 * api匹配成功后的信息
 */
@Data
public class MatchApiDTO {

    /**
     * 资源ID
     */
    private String resourceCode;

    /**
     * 请求唯一标识ID
     */
    private String requestId;
}
