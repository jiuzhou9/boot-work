package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.HelloApiClient;
import com.jiuzhou.bootwork.service.ProductService;
import com.jiuzhou.bootwork.service.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/02/22
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private HelloApiClient productApiClient;

    @Override
    public Result<ProductDTO> getProductInfo(Long productId) {
        return null;
    }

}
