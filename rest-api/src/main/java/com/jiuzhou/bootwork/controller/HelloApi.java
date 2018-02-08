package com.jiuzhou.bootwork.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/02/01
 */
@RestController
@RequestMapping("/hello")
public class HelloApi {

    @GetMapping("")
    public String hello(){
        return "hello";
    }
}
