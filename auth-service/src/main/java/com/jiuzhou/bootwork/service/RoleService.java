package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.dto.RoleDto;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/26
 */
public interface RoleService {

    Long insert(RoleDto roleDto) throws ServiceException;

    /**
     * name查询，模糊查询
     * @param name
     * @return
     */
    List<RoleDto> selectByName(String name) throws ServiceException;

    /**
     * name的绝对查询，如果查询到多条将抛异常
     * @param name
     * @return
     */
    RoleDto selectOneByName(String name) throws ServiceException;

    /**
     * 主键查询
     * @param key
     * @return
     */
    RoleDto selectById(Long key) throws ServiceException;

    boolean updateByKey(RoleDto roleDto) throws ServiceException;

    List<RoleDto> selectAvailableByIds(List<Long> ids) throws ServiceException;

    List<RoleDto> selectAvilable();

}
