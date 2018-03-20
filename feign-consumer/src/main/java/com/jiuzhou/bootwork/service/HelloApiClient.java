package com.jiuzhou.bootwork.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/17
 */
//name属性是服务提供者的应用名
@FeignClient(name = "eureka-api")
public interface HelloApiClient {

    @RequestMapping(value = "/api/v1/hello", method = RequestMethod.GET)
    String hello();
}
