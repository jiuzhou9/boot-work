package com.jiuzhou.bootwork.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2017/12/25
 */
@RestController
@Api(value = "/hello")
public class HelloController {

    @GetMapping("/hello")
    @ApiOperation(value = "hello")
    public String hello(){
        return "hello";
    }

    @PostMapping("/helloPost")
    @ApiOperation("测试post")
    public String helloPost(@RequestParam String hello){
        return hello + " post";
    }
}
