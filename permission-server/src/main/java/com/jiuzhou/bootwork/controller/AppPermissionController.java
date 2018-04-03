package com.jiuzhou.bootwork.controller;

import com.jiuzhou.bootwork.controller.vo.AppTokenVo;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.RoleResourceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private RoleResourceService roleResourceService;

    @RequestMapping(value = "/decide", method = RequestMethod.POST)
    public ResponseEntity<Result<Boolean>> checkPermission(@RequestBody AppTokenVo appTokenVo){
        Result<Boolean> result = new Result<>();
        String userName = appTokenVo.getUserName();
        String appName = appTokenVo.getAppName();
        String method = appTokenVo.getMethod();
        String serverResource = appTokenVo.getServerResource();
        try {
            boolean decide = roleResourceService.decide(userName, serverResource, appName, method);
            result = Result.buildSuccess(decide);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ServiceException e) {
            e.printStackTrace();
            result.setHttpError(HttpErrorEnum.HAS_NO_AUTHORITY);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}
