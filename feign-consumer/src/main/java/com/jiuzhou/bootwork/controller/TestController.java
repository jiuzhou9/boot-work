package com.jiuzhou.bootwork.controller;

import com.jiuzhou.bootwork.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.EAN;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/20
 */
@RestController
@RequestMapping("/test")
@Api(value = "test")
public class TestController {

    @GetMapping(value = "/test")
    @ApiOperation(value = "test")
    public Result test(){
        return null;
    }
}
