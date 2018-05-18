package com.jiuzhou.bootwork.controller;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.ProductService;
import com.jiuzhou.bootwork.service.dto.ProductDTO;
import com.jiuzhou.bootwork.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/02/22
 */
@RestController
@RequestMapping(value = "/api/v1/product")
@Slf4j
@Api(value = "商品管理")
public class ProductApi {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取商品信息")
    public Result<ProductVO> getProductInfoPath(@PathVariable("id") Long id, HttpServletRequest request){
        System.out.println("客户端端口："+request.getRemotePort());
        System.out.println("服务端端口："+request.getLocalPort());
        log.info(JSON.toJSONString(request.getHeader("User-Agent")));
        return get(id);
    }

    @GetMapping(value = "/get-info")
    @ApiOperation(value = "获取商品信息不是path的形式")
    public Result<ProductVO> getProductInfo(@RequestParam Long id){
        return get(id);
    }


    private Result<ProductVO> get(Long id){
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
