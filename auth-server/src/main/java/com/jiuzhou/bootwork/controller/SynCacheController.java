package com.jiuzhou.bootwork.controller;

import com.jiuzhou.bootwork.cache.AccesskeyCacheManage;
import com.jiuzhou.bootwork.cache.CompanyCacheManager;
import com.jiuzhou.bootwork.cache.QuotaCacheManager;
import com.jiuzhou.bootwork.cache.ResourceCacheManager;
import com.jiuzhou.bootwork.cache.cdto.QuotaCacheDTO;
import com.jiuzhou.bootwork.cache.cdto.ResourceCacheDTO;
import com.jiuzhou.bootwork.cache.config.CacheClient;
import com.jiuzhou.bootwork.controller.base.BaseController;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.AccesskeyService;
import com.jiuzhou.bootwork.service.CompanyService;
import com.jiuzhou.bootwork.service.QuotaService;
import com.jiuzhou.bootwork.service.ResourceService;
import com.jiuzhou.bootwork.service.dto.AccesskeyDTO;
import com.jiuzhou.bootwork.service.dto.CompanyDTO;
import com.jiuzhou.bootwork.service.dto.QuotaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/10/17
 */
@RestController
@RequestMapping(value = "/api/v1/syn-cache")
@Slf4j
@Api(value = "同步网关层各种数据")
public class SynCacheController extends BaseController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceCacheManager resourceCacheManager;

    @Autowired
    private AccesskeyService accesskeyService;

    @Autowired
    private AccesskeyCacheManage accesskeyCacheManage;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyCacheManager companyCacheManager;

    @Autowired
    private QuotaCacheManager quotaCacheManager;

    @Autowired
    private QuotaService quotaService;

    @Resource
    protected CacheClient cacheClient;

    /**
     * 批量同步api资源
     * @return
     */
    /*curl --request GET \
                    --url http://localhost:15101/api/v1/syn-cache/resource \
                    --header 'Postman-Token: 24353006-d509-491f-8cc5-4ea2c810c42c' \
                    --header 'cache-control: no-cache'*/
    @ApiOperation(value = "批量同步'资源'")
    @GetMapping(value = "resource")
    public Result<Boolean> synResource(){

        List<ResourceCacheDTO> resourcesToCaches = resourceService.getResourcesToCache();

        cacheClient.deleteAllByPattern(resourceCacheManager.getPreAndMiddleKey(), null);

        resourcesToCaches.forEach(resourceCacheDTO -> {
            resourceCacheManager.putForever(resourceCacheDTO);
        });

        return Result.buildSuccess(true);
    }

    /**
     * 批量同步Accesskey
     * @return
     */
    /*curl --request GET \
                    --url http://localhost:15101/api/v1/syn-cache/accesskey \
                    --header 'Postman-Token: 24353006-d509-491f-8cc5-4ea2c810c42c' \
                    --header 'cache-control: no-cache'*/
    @ApiOperation(value = "批量同步'Accesskey'")
    @GetMapping(value = "accesskey")
    public Result<Boolean> synAccesskey(){

        List<AccesskeyDTO> accesskeyDTOS = accesskeyService.getAllAvailable();

        cacheClient.deleteAllByPattern(accesskeyCacheManage.getPreAndMiddleKey(), null);

        accesskeyDTOS.forEach(accesskeyDTO -> {
            accesskeyCacheManage.putForever(accesskeyDTO);
        });
        return Result.buildSuccess(true);
    }

    /**
     * 批量同步company
     * @return
     */
    /*curl --request GET \
                    --url http://localhost:15101/api/v1/syn-cache/company \
                    --header 'Postman-Token: 24353006-d509-491f-8cc5-4ea2c810c42c' \
                    --header 'cache-control: no-cache'*/
    @ApiOperation(value = "批量同步company")
    @GetMapping(value = "company")
    public Result<Boolean> synCompany(){

        List<CompanyDTO> companyDTOS = companyService.getAllAvailable();

        cacheClient.deleteAllByPattern(companyCacheManager.getPreAndMiddleKey(), null);

        companyDTOS.forEach(companyDTO -> {
            companyCacheManager.putForever(companyDTO);
        });
        return Result.buildSuccess(true);
    }

    /**
     * 批量同步quota
     * @return
     */
    /*curl --request GET \
                    --url http://localhost:15101/api/v1/syn-cache/quota \
                    --header 'Postman-Token: 24353006-d509-491f-8cc5-4ea2c810c42c' \
                    --header 'cache-control: no-cache'*/
    @ApiOperation(value = "批量同步'quota'")
    @GetMapping(value = "quota")
    public Result<Boolean> synQuota(){

        List<QuotaDTO> quotaDTOS = quotaService.getAllAvailable();

        cacheClient.deleteAllByPattern(quotaCacheManager.getPreAndMiddleKey(), null);

        for (QuotaDTO quotaDTO : quotaDTOS) {
            QuotaCacheDTO quotaCacheDTO = new QuotaCacheDTO();
            quotaCacheDTO.setQuotaKey(quotaDTO.getKey() + quotaDTO.getResourceCode());
            quotaCacheDTO.setQuotaDTO(quotaDTO);
            quotaCacheManager.putForever(quotaCacheDTO);
        }
        return Result.buildSuccess(true);
    }

}
