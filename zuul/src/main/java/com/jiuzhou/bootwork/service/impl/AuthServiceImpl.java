package com.jiuzhou.bootwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiuzhou.bootwork.excep.ApiGateWayException;
import com.jiuzhou.bootwork.excep.HttpError;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.AuthServerClient;
import com.jiuzhou.bootwork.service.AuthService;
import com.jiuzhou.bootwork.service.dto.ApiRequestDTO;
import com.jiuzhou.bootwork.service.dto.AuthenticateResultDTO;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/04/13
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthServerClient authServerClient;

    @Override
    public AuthenticateResultDTO authenticate(ApiRequestDTO apiRequestDTO) throws ApiGateWayException {
        Result<AuthenticateResultDTO> result = null;
        try {
            log.info("校验请求开始:::>>>>" + JSON.toJSONString(apiRequestDTO));
            result = authServerClient.authenticate(apiRequestDTO);
            log.info("校验请求结果：" + JSON.toJSONString(result));
        } catch (FeignException e) {
            e.printStackTrace();
            String eMessage = e.getMessage();
            log.error("请求校验异常:::>>>>" + eMessage);
            String[] split = eMessage.split("content:");
            JSONObject jsonObject = JSONObject.parseObject(split[1]);
            String code = (String) jsonObject.get("code");
            HttpError error = HttpErrorEnum.getError(code);
            throw new ApiGateWayException(error);
        }
        AuthenticateResultDTO data = result.getData();
        return data;
    }
}
