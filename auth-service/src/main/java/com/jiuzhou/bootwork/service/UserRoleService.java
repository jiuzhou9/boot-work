package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.dto.RoleDto;
import com.jiuzhou.bootwork.service.dto.UserRoleDto;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/27
 */
public interface UserRoleService {

    Long insert(UserRoleDto userRoleDto) throws ServiceException;

    List<RoleDto> selectOneByUsername(String username) throws ServiceException;

    boolean updateById(UserRoleDto userRoleDto) throws ServiceException;

    List<RoleDto> selectOneByMobile(String mobile) throws ServiceException;

    List<RoleDto> selectAvailableByUserId(Long userId) throws ServiceException;

    UserRoleDto selectByUserIdRoleId(Long userId, Long roleId) throws ServiceException;

    UserRoleDto selectById(Long id) throws ServiceException;

}
