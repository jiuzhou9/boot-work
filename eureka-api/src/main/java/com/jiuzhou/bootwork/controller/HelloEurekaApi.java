package com.jiuzhou.bootwork.controller;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.logging.Logger;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/02/16
 */
@RestController
@RequestMapping(value = "/api/v1/example")
@Slf4j
@Api(value = "eureka-api 第一个服务接口")
public class HelloEurekaApi {

    private final Logger logger = Logger.getLogger(HelloEurekaApi.class.getName());

    @GetMapping(value = "/get")
    @ApiOperation(value = "get 方法")
    public Result<ProductVO> get(@RequestParam Long id){
        Result<ProductVO> result = null;
        ProductVO productVO = new ProductVO();
        productVO.setId(id);
        productVO.setName("华为 P20 Pro");
        productVO.setPrice(new BigDecimal(9999));
        result = Result.buildSuccess(productVO);
        return result;
    }

    @PostMapping(value = "/post")
    @ApiOperation(value = "post 方法")
    public Result<String> post(@RequestBody ProductVO productVO){
        Result<String> result = new Result<>();
        result = Result.buildSuccess("你真棒！");
        return result;
    }

}
