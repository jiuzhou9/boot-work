package com.jiuzhou.bootwork.dao.mapper;

import com.jiuzhou.bootwork.dao.model.Quota;
import com.jiuzhou.bootwork.dao.model.QuotaExample;
import com.jiuzhou.bootwork.dao.model.QuotaKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuotaMapper {
    long countByExample(QuotaExample example);

    int deleteByExample(QuotaExample example);

    int deleteByPrimaryKey(QuotaKey key);

    int insert(Quota record);

    int insertSelective(Quota record);

    List<Quota> selectByExample(QuotaExample example);

    Quota selectByPrimaryKey(QuotaKey key);

    int updateByExampleSelective(@Param("record") Quota record, @Param("example") QuotaExample example);

    int updateByExample(@Param("record") Quota record, @Param("example") QuotaExample example);

    int updateByPrimaryKeySelective(Quota record);

    int updateByPrimaryKey(Quota record);
}