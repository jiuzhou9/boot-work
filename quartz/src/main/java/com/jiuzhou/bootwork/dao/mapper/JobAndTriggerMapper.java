package com.jiuzhou.bootwork.dao.mapper;

import com.jiuzhou.bootwork.dao.model.JobAndTrigger;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobAndTriggerMapper {
	List<JobAndTrigger> getJobAndTriggerDetails();
}
