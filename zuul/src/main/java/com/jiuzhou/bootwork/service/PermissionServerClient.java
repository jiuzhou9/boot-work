package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.dto.AppTokenDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/04/03
 */
@FeignClient(name = "permission-server")
public interface PermissionServerClient {


    @RequestMapping(value = "/api/v1/app-permission/decide", method = RequestMethod.POST)
    ResponseEntity<Result<Boolean>> checkAppToken(@RequestBody AppTokenDto appTokenDto);
}