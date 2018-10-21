package com.jiuzhou.bootwork.controller;

import com.jiuzhou.bootwork.controller.base.BaseController;
import com.jiuzhou.bootwork.controller.cache.LoadAndInit;
import com.jiuzhou.bootwork.controller.vo.ApiRequestVO;
import com.jiuzhou.bootwork.controller.vo.AuthenticateResultVO;
import com.jiuzhou.bootwork.controller.vo.UpdateQuotaVO;
import com.jiuzhou.bootwork.excep.ApiGateWayException;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.AccesskeyService;
import com.jiuzhou.bootwork.service.QuotaService;
import com.jiuzhou.bootwork.service.dto.ApiRequestDTO;
import com.jiuzhou.bootwork.service.dto.AuthenticateResultDTO;
import com.jiuzhou.bootwork.service.dto.UpdateQuotaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/16
 */
@RestController
@RequestMapping(value = "/api/v1/access-key")
@Slf4j
@Api(value = "access-key 主要用于key(APP)的令牌的正确性等校验")
public class AccessKeyController extends BaseController {

    /*@Value("${redis.preKey}")
    private String REDIS_PREKEY;*/

    /*@Autowired
    private RequestCountCacheManager requestCountCacheManager;*/

    @Autowired
    private AccesskeyService accesskeyService;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private LoadAndInit loadAndInit;

    /*@Resource
    protected CacheClient cacheClient;*/

    /**
     * @param apiRequestVO
     * @return
     */
    @PostMapping(value = "/authenticate")
    @ApiOperation(value = "认证key(app)令牌的正确性")
    public Result<AuthenticateResultVO> authenticate(@RequestBody ApiRequestVO apiRequestVO)
                    throws ApiGateWayException {

        ApiRequestDTO apiRequestDTO = new ApiRequestDTO();
        BeanUtils.copyProperties(apiRequestVO, apiRequestDTO);

        AuthenticateResultDTO authenticate = accesskeyService.authenticate(apiRequestDTO);
        AuthenticateResultVO authenticateResultVO = new AuthenticateResultVO();
        BeanUtils.copyProperties(authenticate, authenticateResultVO);

        return Result.buildSuccess(authenticateResultVO);
    }

    @PostMapping(value = "/update-quota-cache")
    @ApiOperation(value = "更新API的调用Redis额度，可以增加或者减少,因为是更新缓存对象中的'额度次数量'字段所以此接口是提供给api回调用的")
    public Result<Boolean> updateQuota(@RequestBody UpdateQuotaVO updateQuotaVO) throws ApiGateWayException {
        UpdateQuotaDTO updateQuotaDTO = new UpdateQuotaDTO();
        BeanUtils.copyProperties(updateQuotaVO, updateQuotaDTO);
        quotaService.updateRedisQuota(updateQuotaDTO);
        return Result.buildSuccess(true);
    }

    @GetMapping(value = "/init-cache")
    @ApiOperation(value = "应急后门接口，清空所有的缓存数据，然后重新初始化缓存，'GATEWAY_AUTH_SERVER_REQUEST_COUNT'除外")
    public Result<Boolean> initCache(){
        /*List<String> list = new ArrayList<>();
        list.add(requestCountCacheManager.getKey(""));
        cacheClient.deleteAllByPattern(REDIS_PREKEY, list);*/
        loadAndInit.initCache();
        return Result.buildSuccess(true);
    }

}