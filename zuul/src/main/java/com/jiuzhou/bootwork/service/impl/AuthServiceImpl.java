package com.jiuzhou.bootwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.constants.TimeStampConstants;
import com.jiuzhou.bootwork.excep.HttpError;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.AuthServerClient;
import com.jiuzhou.bootwork.service.AuthService;
import com.jiuzhou.bootwork.service.PermissionServerClient;
import com.jiuzhou.bootwork.service.dto.AppTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;

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

    @Override
    public boolean checkTimestampAndAutograph(String timestamp, String autograph, String appCode, String appToken)
                    throws ServiceException {
        if (StringUtils.isEmpty(timestamp)){
            throw new ServiceException(HttpErrorEnum.TIME_AUTOGRAPH_NO_RIGHT);
        }
        checkTimestamp(timestamp);

        if (StringUtils.isEmpty(autograph)){
            throw new ServiceException(HttpErrorEnum.TIME_AUTOGRAPH_NO_RIGHT);
        }
        if (StringUtils.isEmpty(appCode)){
            throw new ServiceException(HttpErrorEnum.APP_CODE_IS_EMPTY);
        }
        if (StringUtils.isEmpty(appToken)){
            throw new ServiceException(HttpErrorEnum.APP_TOKEN_IS_EMPTY);
        }
        checkAutograph(autograph, appCode, appToken, timestamp);
        return true;
    }

    /**
     * 校验签名 将APP code + APP token + timestamp 放在一起进行签名计算，判断是否是同一个算法
     * @param autograph
     * @param appCode
     * @param appToken
     * @return 只有true的时候才会返回
     * @throws ServiceException 除了true之外都会抛异常
     */
    private boolean checkAutograph(String autograph, String appCode, String appToken, String timestamp) throws ServiceException {
        String clientValue = appCode + appToken + timestamp;
        String clientAutograph = DigestUtils.sha1Hex(clientValue.getBytes(Charset.forName("UTF-8")));
        if (clientAutograph.equals(autograph)){
            return true;
        }else {
            throw new ServiceException(HttpErrorEnum.TIME_AUTOGRAPH_NO_RIGHT);
        }
    }

    /**
     * 校验时间差，客户端时间与网关服务器时间差只有五分钟时间差
     * @param timestamp
     * @return
     */
    private boolean checkTimestamp(String timestamp) throws ServiceException {
        long l = 0;
        try {
            l = Long.parseLong(timestamp);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new ServiceException(HttpErrorEnum.TIME_AUTOGRAPH_NO_RIGHT);
        }
        long l1 = System.currentTimeMillis();
        boolean b = (l1 - l) < TimeStampConstants.CLIENT_REQUEST_OVERDUE;
        if (b){
            return true;
        }else {
            throw new ServiceException(HttpErrorEnum.TIME_AUTOGRAPH_NO_RIGHT);
        }
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
        dealResult(body, HttpErrorEnum.APP_PERMISSION_CHECK_FAILED);
        return true;
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
        dealResult(body, HttpErrorEnum.APP_TOKEN_CHECK_FAILED);
        appTokenDto = body.getData();
        return appTokenDto;
    }

    /**
     * 处理zuul与认证、鉴权服务之间的异常情况
     * @param result
     * @param httpError
     * @throws ServiceException
     */
    private void dealResult(Result result, HttpError httpError) throws ServiceException {
        if (result != null && Result.SUCCESS_CODE.equals(result.getCode())){
            return;
        }else if (!Result.SUCCESS_CODE.equals(result.getCode())) {
            HttpError error = HttpErrorEnum.getError(result.getCode());
            if (error != null){
                throw new ServiceException(error);
            }else {
                throw new ServiceException(httpError);
            }
        }else {
            throw new ServiceException(httpError);
        }
    }

}
