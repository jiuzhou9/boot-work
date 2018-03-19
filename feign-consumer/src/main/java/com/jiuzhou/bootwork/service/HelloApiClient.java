package com.jiuzhou.bootwork.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/17
 */
//name属性是服务提供者的应用名
@FeignClient(name = "eureka-api")
public interface HelloApiClient {

    @GetMapping(value = "/api/v1/hello")
    String hello();
}
