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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/28
 */
@RestController
@RequestMapping(value = "/api/v1/user")
@Slf4j
@Api(value = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册，注册成功后返回用户临时令牌，该令牌有效时间为3600秒")
    public ResponseEntity<Result<UserTokenVo>> register(@RequestBody UserVo userVo) {
        Result<UserTokenVo> result = new Result<>();

        try {
            if (userVo == null){
                throw new ServiceException(HttpErrorEnum.USER_IS_EMPTY);
            }

            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userVo, userDto);

            UserTokenDto userTokenDto = userService.register(userDto);
            UserTokenVo userTokenVo = new UserTokenVo();
            BeanUtils.copyProperties(userTokenDto, userTokenVo);
            result = Result.buildSuccess(userTokenVo);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ServiceException e) {
            e.printStackTrace();
            log.info(JSON.toJSONString("用户注册失败："+e.getMessage()));
            result.setHttpError(e.getHttpError());
            return new ResponseEntity<>(result, e.getHttpError().getHttpStatus());
        }
    }

    @RequestMapping(value = "/create-user-token", method = RequestMethod.POST)
    @ApiOperation(value = "返回用户临时令牌，该令牌有效时间为3600秒,username/password必须非空")
    public ResponseEntity<Result<UserTokenVo>> createUserToken(@RequestBody UserVo userVo){
        Result<UserTokenVo> result = new Result<>();
        String username = userVo.getUsername();
        String password = userVo.getPassword();
        try {
            if (StringUtils.isEmpty(username)){
                throw new ServiceException(HttpErrorEnum.USERNAME_PARAMETER_IS_EMPTY);
            }else if (StringUtils.isEmpty(password)){
                throw new ServiceException(HttpErrorEnum.PASSWORD_PARAMETER_IS_EMPTY);
            }else {
                UserTokenDto userTokenDto = userService.login(username, password);
                UserTokenVo userTokenVo = new UserTokenVo();
                BeanUtils.copyProperties(userTokenDto, userTokenVo);
                result = Result.buildSuccess(userTokenVo);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.setHttpError(e.getHttpError());
            return new ResponseEntity<>(result, e.getHttpError().getHttpStatus());
        }
    }

//    @RequestMapping(value = "/check-user-token", method = RequestMethod.GET)
//    @ApiOperation(value = "校验用户token")
//    public ResponseEntity<Result<Boolean>> checkUserToken(String userToken){
//        Result result;
//        try {
//            boolean b = userService.checkUserToken(userToken);
//            result = Result.buildSuccess(b);
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//            result = Result.buildFailed(e.getHttpError());
//            return new ResponseEntity<>(result, e.getHttpError().getHttpStatus());
//        } catch (SignatureException e){
//            e.printStackTrace();
//            result = Result.buildFailed(HttpErrorEnum.USER_TOKEN_IS_NOT_RIGHT);
//            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
//        }
//    }
}
