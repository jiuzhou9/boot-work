package com.jiuzhou.bootwork.quartz.dao.mapper;


import com.jiuzhou.bootwork.quartz.dao.model.JobAndTrigger;

import java.util.List;

public interface JobAndTriggerMapper {
	List<JobAndTrigger> getJobAndTriggerDetails();
}
