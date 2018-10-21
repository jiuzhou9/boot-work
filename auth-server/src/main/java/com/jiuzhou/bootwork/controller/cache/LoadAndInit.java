package com.jiuzhou.bootwork.controller.cache;

import com.jiuzhou.bootwork.cache.AccesskeyCacheManage;
import com.jiuzhou.bootwork.cache.CompanyCacheManager;
import com.jiuzhou.bootwork.cache.QuotaCacheManager;
import com.jiuzhou.bootwork.cache.RequestCountCacheManager;
import com.jiuzhou.bootwork.cache.ResourceCacheManager;
import com.jiuzhou.bootwork.cache.cdto.QuotaCacheDTO;
import com.jiuzhou.bootwork.cache.cdto.ResourceCacheDTO;
import com.jiuzhou.bootwork.cache.config.CacheClient;
import com.jiuzhou.bootwork.service.AccesskeyService;
import com.jiuzhou.bootwork.service.CompanyService;
import com.jiuzhou.bootwork.service.QuotaService;
import com.jiuzhou.bootwork.service.ResourceService;
import com.jiuzhou.bootwork.service.dto.AccesskeyDTO;
import com.jiuzhou.bootwork.service.dto.CompanyDTO;
import com.jiuzhou.bootwork.service.dto.QuotaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/15
 *
 * 项目启动加载全部的有效的资源进Redis中
 */
@Component
public class LoadAndInit implements CommandLineRunner {

    @Value("${redis.preKey}")
    private String REDIS_PREKEY;

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

    @Autowired
    private RequestCountCacheManager requestCountCacheManager;

    @Resource
    protected CacheClient cacheClient;


    @Override
    public void run(String... args) throws Exception {
        initCache();

        System.out.println("--------------");
    }

    /**
     * 初始化缓存
     */
    public void initCache() {

        //获取资源集合，待缓存
        List<ResourceCacheDTO> resourceCacheDTOS = resourceService.getResourcesToCache();
        //加载key
        List<AccesskeyDTO> accesskeyDTOS = accesskeyService.getAllAvailable();
        //加载company
        List<CompanyDTO> companyDTOS = companyService.getAllAvailable();
        //初始化额度
        List<QuotaDTO> quotaDTOS = quotaService.getAllAvailable();

        //清除Redis中缓存，前缀：GATEWAY_AUTH_SERVER_；GATEWAY_AUTH_SERVER_REQUEST_COUNT除外
        List<String> list = new ArrayList<>();
        list.add(requestCountCacheManager.getPreAndMiddleKey());
        list.add(resourceCacheManager.getPreAndMiddleKey());
        cacheClient.deleteAllByPattern(REDIS_PREKEY, list);

        //缓存资源
        resourceCacheDTOS.forEach(resourceCacheDTO -> {
            resourceCacheManager.putForever(resourceCacheDTO);
        });
        //缓存accessKey
        for (AccesskeyDTO accesskeyDTO : accesskeyDTOS) {
            accesskeyCacheManage.putForever(accesskeyDTO);
        }
        //缓存company
        for (CompanyDTO companyDTO : companyDTOS) {
            companyCacheManager.putForever(companyDTO);
        }
        //缓存quota
        for (QuotaDTO quotaDTO : quotaDTOS) {
            QuotaCacheDTO quotaCacheDTO = new QuotaCacheDTO();
            quotaCacheDTO.setQuotaKey(quotaDTO.getKey() + quotaDTO.getResourceCode());
            quotaCacheDTO.setQuotaDTO(quotaDTO);
            quotaCacheManager.putForever(quotaCacheDTO);
        }
    }


    /**
     * 加载Resource
     */
    @Deprecated
    private void loadResource() {
        List<ResourceCacheDTO> resourceCacheDTOS = resourceService.getResourcesToCache();

        resourceCacheDTOS.forEach(resourceCacheDTO -> {
            resourceCacheManager.putForever(resourceCacheDTO);
        });
    }

}
