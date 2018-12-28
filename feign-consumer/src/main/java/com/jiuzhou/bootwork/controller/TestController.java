package com.jiuzhou.bootwork.controller;

import com.jiuzhou.bootwork.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/20
 */
@RestController
@RequestMapping("/test")
@Api(value = "test")
public class TestController {

    @GetMapping(value = "/test/resources")
    @ApiOperation(value = "test")
    public Result test(@RequestParam String name){
        return null;
    }

    @GetMapping(value = "/test/resources/{id}")
    @ApiOperation(value = "test1")
    public Result test1(@PathVariable String id){
        return Result.buildSuccess(id);
    }

    @GetMapping(value = "/test1/{age}/{id}")
    @ApiOperation(value = "test2")
    public Result test2(@PathVariable int age, @PathVariable int id){
        return Result.buildSuccess(age + id);
    }


}
