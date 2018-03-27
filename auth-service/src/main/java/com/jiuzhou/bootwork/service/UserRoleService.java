package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.service.dto.RoleDto;
import com.jiuzhou.bootwork.service.dto.UserRoleDto;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/27
 */
public interface UserRoleService {

    Long insert(UserRoleDto userRoleDto) throws Exception;

    List<RoleDto> selectOneByUsername(String username) throws Exception;

    boolean updateById(UserRoleDto userRoleDto) throws Exception;

    List<RoleDto> selectOneByMobile(String mobile) throws Exception;

    List<RoleDto> selectByUserId(Long userId) throws Exception;

    UserRoleDto selectByUserIdRoleId(Long userId, Long roleId) throws Exception;

    UserRoleDto selectById(Long id) throws Exception;

}
