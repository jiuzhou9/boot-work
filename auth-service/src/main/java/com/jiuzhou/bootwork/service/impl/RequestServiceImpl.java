package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.cache.RequestCountCacheManager;
import com.jiuzhou.bootwork.common.StringUtilsSSKJ;
import com.jiuzhou.bootwork.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/13
 */
@Service
@Slf4j
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestCountCacheManager requestCacheManager;

    @Override
    public String getRequestId(String pre) {
        return buildRequestId(pre);
    }

    private String buildRequestId(String serverName) {
        String requestId;
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonth().getValue();
        int dayOfMonth = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        long id = requestCacheManager.getRedisRequestCount();

        requestId = (StringUtils.isEmpty(serverName)? "" : serverName + "-") + StringUtilsSSKJ.format2(String.valueOf(year)) + StringUtilsSSKJ
                        .format2(String.valueOf(month)) + StringUtilsSSKJ.format2(String.valueOf(dayOfMonth)) + StringUtilsSSKJ
                        .format2(String.valueOf(hour)) + StringUtilsSSKJ.format2(String.valueOf(minute)) + StringUtilsSSKJ
                        .format2(String.valueOf(second)) + StringUtilsSSKJ.format6(String.valueOf(id));
        return requestId;
    }

    @Override
    public String getRequestId() {
        return buildRequestId(null);
    }
}
