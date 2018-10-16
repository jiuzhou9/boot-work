package com.jiuzhou.bootwork.service;


import com.github.pagehelper.PageInfo;
import com.jiuzhou.bootwork.dao.model.JobAndTrigger;

public interface IJobAndTriggerService {
	PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize);
}