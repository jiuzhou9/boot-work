package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.service.dto.ResourceDto;

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
    ResourceDto insert(ResourceDto resourceDto) throws Exception;

    /**
     * 条件查询(绝对查询)
     * @param name
     * @return
     */
    ResourceDto getByName(String name);

    /**
     * 根据URL插叙（绝对查询）
     * @param url
     * @return
     */
    ResourceDto getByUrl(String url);

    /**
     * 根据id更新
     * @param resourceDto
     * @param id
     * @return
     */
    ResourceDto updateById(ResourceDto resourceDto, Integer id);
}
