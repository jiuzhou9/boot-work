package com.jiuzhou.bootwork.dao.mapper;

import com.jiuzhou.bootwork.dao.model.RoleResource;
import com.jiuzhou.bootwork.dao.model.RoleResourceExample;
import com.jiuzhou.bootwork.dao.model.RoleResourceKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleResourceMapper {
    long countByExample(RoleResourceExample example);

    int deleteByExample(RoleResourceExample example);

    int deleteByPrimaryKey(RoleResourceKey key);

    int insert(RoleResource record);

    int insertSelective(RoleResource record);

    List<RoleResource> selectByExample(RoleResourceExample example);

    RoleResource selectByPrimaryKey(RoleResourceKey key);

    int updateByExampleSelective(@Param("record") RoleResource record, @Param("example") RoleResourceExample example);

    int updateByExample(@Param("record") RoleResource record, @Param("example") RoleResourceExample example);

    int updateByPrimaryKeySelective(RoleResource record);

    int updateByPrimaryKey(RoleResource record);
}