package com.jiuzhou.bootwork.quartz.job;

import com.jiuzhou.bootwork.cache.AccesskeyCacheManage;
import com.jiuzhou.bootwork.cache.config.CacheClient;
import com.jiuzhou.bootwork.service.AccesskeyService;
import com.jiuzhou.bootwork.service.dto.AccesskeyDTO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Slf4j
public class AccesskeyJob implements BaseJob {

    @Autowired
    private AccesskeyService accesskeyService;

    @Autowired
    private AccesskeyCacheManage accesskeyCacheManage;

    @Autowired
    private CacheClient cacheClient;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.warn("resource Job执行时间: " + new Date());
        List<AccesskeyDTO> accesskeyDTOS = accesskeyService.getAllAvailable();

        String preAndMiddleKey = accesskeyCacheManage.getPreAndMiddleKey();
        cacheClient.deleteAllByPattern(preAndMiddleKey, null);

        for (AccesskeyDTO accesskeyDTO : accesskeyDTOS) {
            accesskeyCacheManage.putForever(accesskeyDTO);
        }
    }
}