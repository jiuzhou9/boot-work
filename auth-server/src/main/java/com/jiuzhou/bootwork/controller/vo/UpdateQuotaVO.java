package com.jiuzhou.bootwork.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/19
 */
@Data
public class UpdateQuotaVO {

    @ApiModelProperty(value = "指定key")
    private String key;

    @ApiModelProperty(value = "指定资源code")
    private String resourceCode;

    @ApiModelProperty(value = "更新额度，正数就是增加，负数就是减少")
    private Long updateQuota;
}
