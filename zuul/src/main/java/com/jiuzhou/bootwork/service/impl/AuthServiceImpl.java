package com.jiuzhou.bootwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
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
        boolean b = checkAuth(appToken, code);
        if (b){
            //权限校验
        }
        return true;
    }

    /**
     * 校验APP令牌
     * @param appToken
     * @param code
     * @return
     * @throws ServiceException
     */
    private boolean checkAuth(String appToken, String code) throws ServiceException {
        AppTokenDto appTokenDto = new AppTokenDto();
        appTokenDto.setAppToken(appToken);
        appTokenDto.setCode(code);
        ResponseEntity<Result<AppTokenDto>> resultResponseEntity = authServerClient.checkAppToken(appTokenDto);
        Result<AppTokenDto> body = resultResponseEntity.getBody();
        if (body != null && Result.SUCCESS_CODE.equals(body.getCode())){
            return true;
        }else {
            log.info("app 令牌身份认证失败："+JSON.toJSONString(body));
            log.info("app 令牌身份认证失败参数appToken："+appToken + " code参数：" + code);
            throw new ServiceException(HttpErrorEnum.APP_TOKEN_CHECK_FAILED);
        }
    }
}
