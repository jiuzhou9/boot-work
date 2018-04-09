package com.jiuzhou.bootwork.schedule;

import com.jiuzhou.bootwork.service.RoleResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wangjiuzhou
 */
@Component
public class ScheduledLoadPermission {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledLoadPermission.class);

    @Autowired
    private RoleResourceService roleResourceService;

    //FIXME #1 开发 测试 生产 需要根据需要进行详细调整配置频率
    //    @Scheduled(cron="0 0/1 8-20 * * ?")//时间：每天8点到20点  频率：每30分钟执行一次
    //    @Scheduled(cron="0/60 * * * * ?")//频率60秒   此频率为开发频率参数值
    //频率10秒   此频率为开发频率参数值
    @Scheduled(cron = "0/10 * * * * ?")
    public void executeFileDownLoadTask() {
        Thread current = Thread.currentThread();
        roleResourceService.loadPermission();
        logger.info("ScheduledLoadPermission.executeFileDownLoadTask 定时任务1:" + current.getId() + ",name:" + current
                        .getName());
    }
}
