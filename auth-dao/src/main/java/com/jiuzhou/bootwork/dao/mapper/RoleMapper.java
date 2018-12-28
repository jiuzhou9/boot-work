package com.jiuzhou.bootwork.dao.mapper;

import com.jiuzhou.bootwork.dao.model.Role;
import com.jiuzhou.bootwork.dao.model.RoleExample;
import com.jiuzhou.bootwork.dao.model.RoleKey;
import com.jiuzhou.bootwork.dao.model.RoleWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(RoleKey key);

    int insert(RoleWithBLOBs record);

    int insertSelective(RoleWithBLOBs record);

    List<RoleWithBLOBs> selectByExampleWithBLOBs(RoleExample example);

    List<Role> selectByExample(RoleExample example);

    RoleWithBLOBs selectByPrimaryKey(RoleKey key);

    int updateByExampleSelective(@Param("record") RoleWithBLOBs record, @Param("example") RoleExample example);

    int updateByExampleWithBLOBs(@Param("record") RoleWithBLOBs record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(RoleWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(RoleWithBLOBs record);

    int updateByPrimaryKey(Role record);
}