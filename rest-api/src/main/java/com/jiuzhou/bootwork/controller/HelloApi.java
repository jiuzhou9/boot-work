package com.jiuzhou.bootwork.controller;

import com.jiuzhou.bootwork.basecontroller.BaseController;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.exception.ApiException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/02/01
 */
@RestController
@RequestMapping("/hello")
public class HelloApi extends BaseController {

    @GetMapping("")
    public String hello(){
        return "hello world 234567890";
    }

    @GetMapping(value = "test-exception")
    public String testException() throws ApiException {
        throw new ApiException(HttpErrorEnum.USER_ID_ROLE_ID_QUERY_MANY_RESULTS);
    }
}
