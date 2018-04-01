package com.jiuzhou.bootwork.controller;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.controller.vo.AppTokenVo;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.AppService;
import com.jiuzhou.bootwork.service.dto.AppTokenDto;
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
 * @date 2018/03/31
 */
@RestController
@RequestMapping(value = "/api/v1/app")
@Slf4j
@Api(value = "app管理")
public class AppController {

    @Autowired
    private AppService appService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation("创建APP，返回信息中包含APP令牌、APP code，请求参数中用户令牌、APP name一定非空")
    public ResponseEntity<Result<AppTokenVo>> create(@RequestBody AppTokenVo appTokenVo){
        Result<AppTokenVo> result = new Result<>();
        String appName = appTokenVo.getAppName();
        String userToken = appTokenVo.getUserToken();
        try {
            validateCreate(appName, userToken);
            AppTokenDto appTokenDto = appService.insert(userToken, appName);
            BeanUtils.copyProperties(appTokenDto, appTokenVo);
            result = Result.buildSuccess(appTokenVo);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ServiceException e) {
            e.printStackTrace();
            log.info(JSON.toJSONString(e.getHttpError()));
            result.setHttpError(e.getHttpError());
            return new ResponseEntity<>(result, e.getHttpError().getHttpStatus());
        }
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    @ApiOperation(value = "刷新APP令牌，任何时刻只有一个令牌是可用的")
    public ResponseEntity<Result<AppTokenVo>> refresh(@RequestBody AppTokenVo appTokenVo){
        Result<AppTokenVo> result = new Result<>();
        String appName = appTokenVo.getAppName();
        String userToken = appTokenVo.getUserToken();

        try {
            validateCreate(appName, userToken);
            AppTokenDto appTokenDto = appService.getAppToken(userToken, appName);
            BeanUtils.copyProperties(appTokenDto, appTokenVo);
            result = Result.buildSuccess(appTokenVo);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ServiceException e) {
            e.printStackTrace();
            log.info(JSON.toJSONString(e.getHttpError()));
            result.setHttpError(e.getHttpError());
            return new ResponseEntity<>(result, e.getHttpError().getHttpStatus());
        }
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ApiOperation(value = "app 令牌校验")
    public ResponseEntity<Result<AppTokenVo>> checkToken(@RequestBody AppTokenVo appTokenVo){
        Result<AppTokenVo> result = new Result<>();
        String appToken = appTokenVo.getAppToken();
        String code = appTokenVo.getCode();
        try {
            if (StringUtils.isEmpty(appToken)){
                throw new ServiceException(HttpErrorEnum.APP_TOKEN_IS_EMPTY);
            }else if (StringUtils.isEmpty(code)){
                throw new ServiceException(HttpErrorEnum.APP_CODE_IS_EMPTY);
            }

            AppTokenDto appTokenDto = appService.checkAppToken(appToken, code);
            BeanUtils.copyProperties(appTokenDto, appTokenVo);
            result = Result.buildSuccess(appTokenVo);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ServiceException e) {
            e.printStackTrace();
            log.info(JSON.toJSONString(e.getHttpError()));
            result.setHttpError(e.getHttpError());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

    }



    /**
     * 校验非空
     * @param appName
     * @param userToken
     * @throws ServiceException
     */
    private void validateCreate(String appName, String userToken) throws ServiceException {
        if (StringUtils.isEmpty(appName)){
            throw new ServiceException(HttpErrorEnum.APP_NAME_IS_EMPTY);
        }else if (StringUtils.isEmpty(userToken)){
            throw new ServiceException(HttpErrorEnum.USER_TOKEN_IS_EMPTY);
        }
    }
}
