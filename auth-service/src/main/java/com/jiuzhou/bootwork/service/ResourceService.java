package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.service.dto.ResourceDto;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/23
 */
public interface ResourceService {

    /**
     * 插入成功返回对象，不成功的话抛异常
     * @param resourceDto
     * @return
     */
    Long insert(ResourceDto resourceDto) throws Exception;

    /**
     * 条件查询(绝对查询)
     * @param name
     * @return
     */
    ResourceDto selectOneByName(String name) throws Exception;

    /**
     * 根据URL插叙（绝对查询）
     * @param url
     * @return
     */
    ResourceDto selectOneByUrl(String url) throws Exception;

    /**
     * 根据id更新
     * @param resourceDto
     * @param id
     * @return
     */
    boolean updateById(ResourceDto resourceDto, Long id) throws Exception;

    ResourceDto selectById(Long id) throws Exception;

    List<ResourceDto> selectByIds(List<Long> ids) throws Exception;
}
