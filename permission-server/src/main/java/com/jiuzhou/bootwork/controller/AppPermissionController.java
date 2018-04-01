package com.jiuzhou.bootwork.controller;

import com.jiuzhou.bootwork.controller.vo.AppTokenVo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/04/01
 */
@RestController
@RequestMapping(value = "/api/v1/app-permission")
@Api(value = "APP权限管理")
public class AppPermissionController {

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public void checkPermission(@RequestBody AppTokenVo appTokenVo){

    }
}
