package com.jiuzhou.bootwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.AuthServerClient;
import com.jiuzhou.bootwork.service.AuthService;
import com.jiuzhou.bootwork.service.PermissionServerClient;
import com.jiuzhou.bootwork.service.dto.AppTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/04/01
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthServerClient authServerClient;

    @Autowired
    private PermissionServerClient permissionServerClient;

    @Override
    public boolean checkAuthAndPermission(String appToken, String code, String serverResource, String method) throws ServiceException {
        boolean flag = false;
        AppTokenDto appTokenDto = checkAuth(appToken, code);
        String appName = appTokenDto.getAppName();
        String userName = appTokenDto.getUserName();
        appTokenDto.setMethod(method);
        appTokenDto.setServerResource(serverResource);
        if (!StringUtils.isEmpty(appName) && !StringUtils.isEmpty(userName)){
            flag = checkPermission(appTokenDto);
        }
        return flag;
    }

    /**
     * 校验权限
     * @param appTokenDto
     * @return
     */
    private boolean checkPermission(AppTokenDto appTokenDto) throws ServiceException {
        //权限校验
        ResponseEntity<Result<Boolean>> resultResponseEntity = permissionServerClient
                        .checkAppToken(appTokenDto);
        Result<Boolean> body = resultResponseEntity.getBody();
        if (body != null && Result.SUCCESS_CODE.equals(body.getCode())){
            return true;
        }else {
            log.error("权限校验结果:"+JSON.toJSONString(body));
            log.error("权限参数："+JSON.toJSONString(appTokenDto));
            throw new ServiceException(HttpErrorEnum.HAS_NO_AUTHORITY);
        }

    }

    /**
     * 校验APP令牌
     * @param appToken
     * @param code
     * @return
     * @throws ServiceException
     */
    private AppTokenDto checkAuth(String appToken, String code) throws ServiceException {
        AppTokenDto appTokenDto = new AppTokenDto();
        appTokenDto.setAppToken(appToken);
        appTokenDto.setCode(code);
        ResponseEntity<Result<AppTokenDto>> resultResponseEntity = authServerClient.checkAppToken(appTokenDto);
        Result<AppTokenDto> body = resultResponseEntity.getBody();
        if (body != null && Result.SUCCESS_CODE.equals(body.getCode())){
            appTokenDto = body.getData();
            return appTokenDto;
        }else {
            log.error("app 令牌身份认证失败："+JSON.toJSONString(body));
            log.error("app 令牌身份认证失败参数appToken："+appToken + " code参数：" + code);
            throw new ServiceException(HttpErrorEnum.APP_TOKEN_CHECK_FAILED);
        }
    }

}
