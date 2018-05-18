package com.jiuzhou.bootwork.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/02/22
 */
@Data
public class ProductVO {
    private Long id;
    private String name;
    private BigDecimal price;
}
