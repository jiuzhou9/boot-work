package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.AuthServerClient;
import com.jiuzhou.bootwork.service.dto.ApiRequestDTO;
import com.jiuzhou.bootwork.service.dto.AuthenticateResultDTO;
import org.springframework.stereotype.Component;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/29
 */
@Component
public class FallBack implements AuthServerClient {

    @Override
    public Result<AuthenticateResultDTO> authenticate(ApiRequestDTO apiRequestDTO) {
        Result<AuthenticateResultDTO> result = new Result<>();
        result.setHttpError(HttpErrorEnum.APP_UPDATE_FAILED);
        return result;
    }
}
