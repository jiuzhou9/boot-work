package com.jiuzhou.bootwork.controller;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.controller.vo.UserVo;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.UserService;
import com.jiuzhou.bootwork.service.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @ApiOperation(value = "用户注册")
    public ResponseEntity<Result<UserVo>> register(@RequestBody UserVo userVo) {
        Result<UserVo> result = new Result<>();
        if (userVo == null){
            result.setCode("1");
            result.setMessage("用户信息为空");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userVo, userDto);
        try {
            userService.register(userDto);
        } catch (ServiceException e) {
            e.printStackTrace();
            log.info(JSON.toJSONString("用户注册失败："+e.getMessage()));
            result.setCode(e.getHttpError().getCode());
            result.setMessage(e.getHttpError().getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result = Result.buildSuccess(userVo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
