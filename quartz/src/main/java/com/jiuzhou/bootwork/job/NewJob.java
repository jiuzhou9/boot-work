package com.jiuzhou.bootwork.job;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;  

@Slf4j
public class NewJob implements BaseJob {  

    public NewJob() {

    }  

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.warn("New Job执行时间: " + new Date());

    }  
}