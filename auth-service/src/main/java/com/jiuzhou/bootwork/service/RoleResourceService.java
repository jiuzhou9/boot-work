package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.dto.ResourceDto;
import com.jiuzhou.bootwork.service.dto.RoleDto;
import com.jiuzhou.bootwork.service.dto.RoleResourceDto;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/26
 */
public interface RoleResourceService {

    Long insert(RoleResourceDto roleResourceDto) throws ServiceException;

    /**
     * 绝对查询,根据角色名字查询资源列表
     * @param roleName
     * @return
     */
    List<ResourceDto> selectByRoleName(String roleName) throws ServiceException;

    /**
     * 绝对查询,根据角色ID查询资源列表
     * @param roleId
     * @return
     */
    List<ResourceDto> selectResourceByRoleId(Long roleId) throws ServiceException;

    /**
     * 绝对查询
     * @param resourceId
     * @return
     */
    List<RoleDto> selectRolesByResourceId(Long resourceId) throws ServiceException;

    /**
     * 绝对查询
     * @return
     */
    List<RoleDto> selectRolesByResourceName(String resourceName) throws ServiceException;

    /**
     * 绝对查询
     * @param url
     * @return
     */
    List<RoleDto> selectRolesByResourceUrl(String url) throws ServiceException;

    /**
     * ID更新
     * @param roleResourceDto
     * @return
     */
    boolean updateById(RoleResourceDto roleResourceDto) throws ServiceException;

}
