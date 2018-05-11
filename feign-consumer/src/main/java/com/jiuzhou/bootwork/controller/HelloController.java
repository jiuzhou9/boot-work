package com.jiuzhou.bootwork.controller;

import com.jiuzhou.bootwork.service.HelloApiClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/17
 */
@RestController
@RequestMapping(value = "/hello")
@Api(value = "hello")
@Slf4j
public class HelloController {

    @Autowired
    private HelloApiClient helloApiClient;

    @GetMapping(value = "/test")
    @ApiOperation(value = "test")
    public String test(){
        String hello = helloApiClient.hello();
        return hello;
    }

}
