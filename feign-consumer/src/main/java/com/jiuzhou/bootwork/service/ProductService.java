package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.dto.ProductDTO;
import com.jiuzhou.bootwork.vo.ProductVO;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/20
 */
public interface ProductService {

    /**
     * get请求，path方式传参
     * @param id
     * @return
     */
    Result<ProductVO> getProductInfoPath(Long id);

    /**
     * get请求，非path传参
     * @param id
     * @return
     */
    Result<ProductVO> getProductInfo(Long id);
}
