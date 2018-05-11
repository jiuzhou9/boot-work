package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.dto.AppTokenDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/04/01
 */
@FeignClient(name = "auth-server")
public interface AuthServerClient {

    @RequestMapping(value = "/api/v1/app/check", method = RequestMethod.POST)
    ResponseEntity<Result<AppTokenDto>> checkAppToken(@RequestBody AppTokenDto appTokenDto);
}
