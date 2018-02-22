package com.jiuzhou.bootwork.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/02/20
 */
@RestController
@RequestMapping(value = "/api/v1/consumer")
@Slf4j
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/helloConsumer")
    public String helloConsumer(){
        return restTemplate.getForEntity("http://EUREKA-API/api/v1/hello-eureka/hello", String.class).getBody();
    }
}
