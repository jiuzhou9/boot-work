package com.jiuzhou.bootwork.controller;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.controller.vo.UserTokenVo;
import com.jiuzhou.bootwork.controller.vo.UserVo;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.UserService;
import com.jiuzhou.bootwork.service.dto.UserDto;
import com.jiuzhou.bootwork.service.dto.UserTokenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/28
 */
@RestController
@RequestMapping(value = "api/v1/user")
@Slf4j
@Api(value = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册，注册成功后返回用户临时令牌，该令牌有效时间为3600秒")
    public ResponseEntity<Result<UserTokenVo>> register(@RequestBody UserVo userVo) {
        Result<UserTokenVo> result = new Result<>();
        if (userVo == null){
            result = Result.buildFailed(HttpErrorEnum.USER_IS_EMPTY);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userVo, userDto);
        try {
            UserTokenDto userTokenDto = userService.register(userDto);
            UserTokenVo userTokenVo = new UserTokenVo();
            BeanUtils.copyProperties(userTokenDto, userTokenVo);
            result = Result.buildSuccess(userTokenVo);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ServiceException e) {
            e.printStackTrace();
            log.info(JSON.toJSONString("用户注册失败："+e.getMessage()));
            result.setCode(e.getHttpError().getCode());
            result.setMessage(e.getHttpError().getMessage());
            return new ResponseEntity<>(result, e.getHttpError().getHttpStatus());
        }
    }

    @RequestMapping(value = "/create-user-token", method = RequestMethod.POST)
    @ApiOperation(value = "返回用户临时令牌，该令牌有效时间为3600秒")
    public ResponseEntity<Result<UserTokenVo>> createUserToken(@RequestBody UserVo userVo){
        Result<UserTokenVo> result;
        try {
            UserTokenDto userTokenDto = userService.login(userVo.getUsername(), userVo.getPassword());
            UserTokenVo userTokenVo = new UserTokenVo();
            BeanUtils.copyProperties(userTokenDto, userTokenVo);
            result = Result.buildSuccess(userTokenVo);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ServiceException e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result = Result.buildFailed(e.getHttpError());
            return new ResponseEntity<>(result, e.getHttpError().getHttpStatus());
        }
    }
}
