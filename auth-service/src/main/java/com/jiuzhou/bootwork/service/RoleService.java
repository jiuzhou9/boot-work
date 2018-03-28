package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.service.dto.RoleDto;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/26
 */
public interface RoleService {

    Long insert(RoleDto roleDto) throws Exception;

    /**
     * name查询，模糊查询
     * @param name
     * @return
     */
    List<RoleDto> selectByName(String name) throws Exception;

    /**
     * name的绝对查询，如果查询到多条将抛异常
     * @param name
     * @return
     */
    RoleDto selectOneByName(String name) throws Exception;

    /**
     * 主键查询
     * @param key
     * @return
     */
    RoleDto selectById(Long key) throws Exception;

    boolean updateByKey(RoleDto roleDto) throws Exception;

    List<RoleDto> selectByIds(List<Long> ids) throws Exception;
}