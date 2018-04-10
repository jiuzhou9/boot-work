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

    List<AppRoleDto> selectAvailableByAppId(Long appId) throws ServiceException;

    /**
     * 查询APP 和 角色 映射关系
     * @param appId
     * @param roleId
     * @return
     * @throws ServiceException
     */
    AppRoleDto selectOneByAppIdRoleId(Long appId, Long roleId) throws ServiceException;

}
