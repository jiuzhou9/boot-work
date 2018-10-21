package com.jiuzhou.bootwork.controller;

import com.jiuzhou.bootwork.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/08/09
 */
@RestController
@RequestMapping(value = "/api/v1/cache")
@Slf4j
public class ExampleApi {

    @GetMapping("/init-cache")
    public Result<String> hello(){
        Result<String> result = new Result<>();
        result.setData("你好！");
        return result;
    }
}
