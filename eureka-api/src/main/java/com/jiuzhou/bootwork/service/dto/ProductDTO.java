package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/02/22
 */
@Data
public class ProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
}
