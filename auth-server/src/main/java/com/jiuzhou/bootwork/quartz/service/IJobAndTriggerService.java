package com.jiuzhou.bootwork.quartz.service;


import com.github.pagehelper.PageInfo;
import com.jiuzhou.bootwork.quartz.dao.model.JobAndTrigger;

public interface IJobAndTriggerService {
	PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize);
}