package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.dto.ResourceDto;
import com.jiuzhou.bootwork.service.dto.RoleDto;
import com.jiuzhou.bootwork.service.dto.RoleResourceDto;
import java.util.Collection;
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
    List<RoleDto> selectAvailableRolesByResourceId(Long resourceId) throws ServiceException;

    /**
     * 绝对查询
     * @return
     */
    List<RoleDto> selectRolesByResourceName(String resourceName) throws ServiceException;


    /**
     * ID更新
     * @param roleResourceDto
     * @return
     */
    boolean updateById(RoleResourceDto roleResourceDto) throws ServiceException;

    /**
     * 加载权限
     * @throws ServiceException
     */
    void loadPermission();

    /**
     * 获取有效的角色资源
     * @return
     */
    List<RoleResourceDto> selectAvailable();

    /**
     * 查询资源所需要的角色
     * @param serverResource
     * @param method
     * @return
     */
    Collection<String> getAttributes(String serverResource, String method);

    /**
     * 投票判决，一个第三方是否可以访问一个资源
     *
     * @param username     第三方
     * @param resourcePath 他想访问的资源
     *
     * @return
     */
    boolean decide(String username, String resourcePath, String method) throws ServiceException;

    /**
     * 判断某用户的App是否有权限访问资源
     *
     * @param userName
     * @param resourcePath
     * @param appName
     *
     * @return
     */
    boolean decide(String userName, String resourcePath, String appName, String method) throws ServiceException;

}
