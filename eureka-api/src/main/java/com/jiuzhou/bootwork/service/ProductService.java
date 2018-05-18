package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.dto.ProductDTO;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/02/22
 */
public interface ProductService {

    Result<ProductDTO> getInfo(Long id);
}
