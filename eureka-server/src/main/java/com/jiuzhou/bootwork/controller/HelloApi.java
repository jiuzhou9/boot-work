package com.jiuzhou.bootwork.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/02/13
 */
@RestController(value = "hello")
public class HelloApi {

    @GetMapping(value = "/hello")
    public String hello(){
        return "hello world!";
    }
}
