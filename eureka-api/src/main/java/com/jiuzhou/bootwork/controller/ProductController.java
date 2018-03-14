package com.jiuzhou.bootwork.controller;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.controller.vo.ProductVO;
import com.jiuzhou.bootwork.service.ProductService;
import com.jiuzhou.bootwork.service.dto.ProductDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/02/22
 */
@RestController
@RequestMapping(value = "/api/v1/product")
@Slf4j
@Api(value = "商品管理")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/info")
    @ApiOperation(value = "获取商品信息")
    public Result<ProductVO> getProductInfo(@RequestParam(required = false) Long id){
        log.info("获取商品信息 id:" + id);
//        try {
//            int sleepTime = new Random().nextInt(3000);
//            Thread.sleep(sleepTime);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Result<ProductVO> productVOResult = new Result<>();
        Result<ProductDTO> productDTOResult = productService.getInfo(id);
        if (Result. SUCCESS_CODE.equals(productDTOResult.getCode())){
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(productDTOResult.getData(), productVO);
            productVOResult.setData(productVO);
            return productVOResult;
        }else {
            BeanUtils.copyProperties(productDTOResult, productVOResult);
            return productVOResult;
        }
    }
}
