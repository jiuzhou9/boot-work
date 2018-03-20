package com.jiuzhou.bootwork.controller;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.ProductService;
import com.jiuzhou.bootwork.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/17
 */
@RestController
@RequestMapping(value = "/product")
@Api(value = "product")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/getProductInfoPath")
    @ApiOperation(value = "path类型传参，get 请求")
    public Result<ProductVO> getProductInfoPath(){
        Result<ProductVO> productVOResult = productService.getProductInfoPath(1L);
        return productVOResult;
    }

    @GetMapping(value = "/getProductInfo")
    @ApiOperation(value = "非path类型传参，get 请求")
    public Result<ProductVO> getProductInfo(){
        Result<ProductVO> productVOResult = productService.getProductInfo(1L);
        return productVOResult;
    }

}
