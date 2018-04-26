package com.jiuzhou.bootwork.dao.mapper;

import com.jiuzhou.bootwork.dao.model.UserRole;
import com.jiuzhou.bootwork.dao.model.UserRoleExample;
import com.jiuzhou.bootwork.dao.model.UserRoleKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {
    long countByExample(UserRoleExample example);

    int deleteByExample(UserRoleExample example);

    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    List<UserRole> selectByExample(UserRoleExample example);

    UserRole selectByPrimaryKey(UserRoleKey key);

    int updateByExampleSelective(@Param("record") UserRole record, @Param("example") UserRoleExample example);

    int updateByExample(@Param("record") UserRole record, @Param("example") UserRoleExample example);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    /**
     * 用户角色剩余次数更新（递减）
     * @param id
     * @return
     */
    int updateRemainderById(Long id);
}