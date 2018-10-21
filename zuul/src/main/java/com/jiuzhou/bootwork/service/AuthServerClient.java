package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.dto.ApiRequestDTO;
import com.jiuzhou.bootwork.service.dto.AuthenticateResultDTO;
import com.jiuzhou.bootwork.service.impl.FallBack;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/13
 */
@FeignClient(name = "auth-server", fallback = FallBack.class)
public interface AuthServerClient {

    @PostMapping(value = "/api/v1/access-key/authenticate")
    Result<AuthenticateResultDTO> authenticate(@RequestBody ApiRequestDTO apiRequestDTO);

}
