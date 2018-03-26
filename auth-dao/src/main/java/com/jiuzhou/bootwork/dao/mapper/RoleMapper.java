package com.jiuzhou.bootwork.dao.mapper;

import com.jiuzhou.bootwork.dao.model.Role;
import com.jiuzhou.bootwork.dao.model.RoleExample;
import com.jiuzhou.bootwork.dao.model.RoleKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(RoleKey key);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(RoleKey key);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}