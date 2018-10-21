package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.excep.ApiGateWayException;
import com.jiuzhou.bootwork.service.dto.ApiRequestDTO;
import com.jiuzhou.bootwork.service.dto.AuthenticateResultDTO;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/20
 */
public interface AuthService {

    /**
     * 校验请求是否合法
     * @return
     * @throws ApiGateWayException
     */
    AuthenticateResultDTO authenticate(ApiRequestDTO apiRequestDTO) throws ApiGateWayException;

}
