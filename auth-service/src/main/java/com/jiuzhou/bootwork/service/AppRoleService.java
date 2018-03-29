package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.dto.AppRoleDto;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/27
 */
public interface AppRoleService {

    Long insert(AppRoleDto appRoleDto) throws ServiceException;

    boolean updateById(AppRoleDto appRoleDto) throws ServiceException;

    List<AppRoleDto> selectByAppId(Long appId) throws ServiceException;

    AppRoleDto selectById(Long id) throws ServiceException;

    AppRoleDto selectOneByAppIdRoleId(Long appId, Long roleId) throws ServiceException;

}
