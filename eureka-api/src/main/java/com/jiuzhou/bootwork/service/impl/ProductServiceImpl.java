package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.ProductService;
import com.jiuzhou.bootwork.service.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/02/22
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

    @Override
    public Result<ProductDTO> getInfo(Long id) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("手机");
        productDTO.setPrice(new BigDecimal(12.00));
        return Result.buildSuccess(productDTO);
    }
}
