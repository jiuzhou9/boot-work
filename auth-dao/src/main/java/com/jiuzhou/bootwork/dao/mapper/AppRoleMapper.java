package com.jiuzhou.bootwork.dao.mapper;

import com.jiuzhou.bootwork.dao.model.AppRole;
import com.jiuzhou.bootwork.dao.model.AppRoleExample;
import com.jiuzhou.bootwork.dao.model.AppRoleKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppRoleMapper {
    long countByExample(AppRoleExample example);

    int deleteByExample(AppRoleExample example);

    int deleteByPrimaryKey(AppRoleKey key);

    int insert(AppRole record);

    int insertSelective(AppRole record);

    List<AppRole> selectByExample(AppRoleExample example);

    AppRole selectByPrimaryKey(AppRoleKey key);

    int updateByExampleSelective(@Param("record") AppRole record, @Param("example") AppRoleExample example);

    int updateByExample(@Param("record") AppRole record, @Param("example") AppRoleExample example);

    int updateByPrimaryKeySelective(AppRole record);

    int updateByPrimaryKey(AppRole record);
}