package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.ProductApiClient;
import com.jiuzhou.bootwork.service.ProductService;
import com.jiuzhou.bootwork.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/20
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductApiClient productApiClient;

    @Override
    public Result<ProductVO> getProductInfoPath(Long id) {
        Result<ProductVO> productVOResult = productApiClient.getProductInfoPath(id);
        return productVOResult;
    }

    @Override
    public Result<ProductVO> getProductInfo(Long id) {
        Result<ProductVO> productVOResult = productApiClient.getProductInfo(id);
        return productVOResult;
    }
}
