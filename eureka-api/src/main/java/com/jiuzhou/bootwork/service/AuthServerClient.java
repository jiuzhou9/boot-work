package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.dto.UpdateQuotaDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/05/13
 */
@FeignClient(name = "auth-server")
public interface AuthServerClient {

    @PostMapping(value = "/api/v1/access-key/update-quota")
    Result<Boolean> updateQuota(@RequestBody UpdateQuotaDTO updateQuotaDTO);
}
