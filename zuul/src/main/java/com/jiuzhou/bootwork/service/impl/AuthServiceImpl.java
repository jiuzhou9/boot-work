package com.jiuzhou.bootwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.AuthServerClient;
import com.jiuzhou.bootwork.service.AuthService;
import com.jiuzhou.bootwork.service.dto.AppTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/04/01
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthServerClient authServerClient;

    @Override
    public boolean checkAuthAndPermission(String appToken, String code) throws ServiceException {
        AppTokenDto appTokenDto = new AppTokenDto();
        appTokenDto.setAppToken(appToken);
        appTokenDto.setCode(code);
        ResponseEntity<Result<AppTokenDto>> resultResponseEntity = authServerClient.checkAppToken(appTokenDto);
        log.info(JSON.toJSONString(resultResponseEntity));
        HttpStatus statusCode = resultResponseEntity.getStatusCode();
        if (HttpStatus.OK.equals(statusCode)){
            //权限校验
        }else {
            Result<AppTokenDto> body = resultResponseEntity.getBody();
            throw new ServiceException(body.getHttpError());
        }
        return true;
    }
}
