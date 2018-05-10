package com.jiuzhou.bootwork.controller;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.ConsumerService;
import com.jiuzhou.bootwork.service.dto.ProductDTO;
import com.jiuzhou.bootwork.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/02/20
 */
@RestController
@RequestMapping(value = "/api/v1/consumer")
@Slf4j
@Api(value = "服务消费者")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping(value = "/product-info")
    @ApiOperation(value = "获取商品信息")
    public Result<ProductVO> getProductInfo(@RequestParam Long productId){
        Result<ProductDTO> productDTOResult = consumerService.getProductInfo(productId);
        Result<ProductVO> result = new Result<>();
        BeanUtils.copyProperties(productDTOResult, result);
        return result;
    }
}
