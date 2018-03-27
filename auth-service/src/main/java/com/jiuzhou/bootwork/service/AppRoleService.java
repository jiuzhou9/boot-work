package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.service.dto.AppRoleDto;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/27
 */
public interface AppRoleService {

    Long insert(AppRoleDto appRoleDto) throws Exception;

    boolean updateById(AppRoleDto appRoleDto) throws Exception;

    List<AppRoleDto> selectByAppId(Long appId) throws Exception;

    AppRoleDto selectById(Long id) throws Exception;

    AppRoleDto selectOneByAppIdRoleId(Long appId, Long roleId) throws Exception;

}
