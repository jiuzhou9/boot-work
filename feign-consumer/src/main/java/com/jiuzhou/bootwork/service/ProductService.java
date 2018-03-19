package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.dto.ProductDTO;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/17
 */
public interface ProductService {

    Result<ProductDTO> getProductInfo(Long productId);
}
