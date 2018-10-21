package com.jiuzhou.bootwork.quartz.job;

import com.jiuzhou.bootwork.cache.ResourceCacheManager;
import com.jiuzhou.bootwork.cache.cdto.ResourceCacheDTO;
import com.jiuzhou.bootwork.cache.config.CacheClient;
import com.jiuzhou.bootwork.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Slf4j
public class ResourceJob implements BaseJob {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceCacheManager resourceCacheManager;

    @Autowired
    private CacheClient cacheClient;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.warn("resource Job执行时间: " + new Date());
        List<ResourceCacheDTO> resourcesToCaches = resourceService.getResourcesToCache();

        String preAndMiddleKey = resourceCacheManager.getPreAndMiddleKey();
        cacheClient.deleteAllByPattern(preAndMiddleKey, null);

        resourcesToCaches.forEach(resourceCacheDTO -> {
            resourceCacheManager.putForever(resourceCacheDTO);
        });
    }
}