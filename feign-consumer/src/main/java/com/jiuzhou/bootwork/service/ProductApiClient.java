package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.dto.ProductDTO;
import com.jiuzhou.bootwork.vo.ProductVO;
//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/17
 */
//name属性是服务提供者的应用名
@FeignClient(name = "eureka-api")
public interface ProductApiClient {

    /**
     * value中的地址就是服务的映射地址
     * 两个get请求均可以
     * @param id
     * @return
     */
//    @RequestMapping(value = "/api/v1/product/get-info", method = RequestMethod.GET)
    @GetMapping(value = "/api/v1/product/get-info")
    Result<ProductVO> getProductInfo(@RequestParam(value = "id",defaultValue = "0") Long id);

    /**
     * value中的地址就是服务的映射地址,在URL中传递参数，path方式
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/v1/product/info/{id}", method = RequestMethod.GET)
    Result<ProductVO> getProductInfoPath(@PathVariable("id") Long id);
}
