package com.jiuzhou.bootwork.job;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;  

@Slf4j
public class HelloJob implements BaseJob {  

    public HelloJob() {

    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("============");
        log.warn("Hello Job执行时间: " + new Date());

    }  
}