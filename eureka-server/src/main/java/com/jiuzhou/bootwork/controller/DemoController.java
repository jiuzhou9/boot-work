package com.jiuzhou.bootwork.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/19
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping(value = "/test")
    public void test(){
        return;
    }
}
