package com.jiuzhou.bootwork.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/06/02
 */
@Component
public class Sheduled1 {

    @Scheduled(cron = "0/10 * * * * ?")
    public void doIt(){
        System.out.println("*");
    }
}
