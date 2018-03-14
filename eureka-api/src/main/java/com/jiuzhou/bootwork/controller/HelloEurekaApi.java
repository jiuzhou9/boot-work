package com.jiuzhou.bootwork.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/02/16
 */
@RestController
@RequestMapping(value = "/api/v1/hello-eureka")
@Slf4j
@Api(value = "eureka-api 第一个服务接口")
public class HelloEurekaApi {

    private final Logger logger = Logger.getLogger(HelloEurekaApi.class.getName());

//    @Autowired
//    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/hello")
    @ApiOperation(value = "hello 方法")
    public String hello(@RequestParam(required = false) String name){
//        List<ServiceInstance> instances = discoveryClient.getInstances("EUREKA-API");
//        log.info(JSON.toJSONString(instances));
        return "hello " + name;
    }
}
