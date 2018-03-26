package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.service.dto.ServerDto;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/25
 */
public interface ServerService {

    Long insert(ServerDto serverDto) throws Exception;

    /**
     * name查询，模糊查询
     * @param name
     * @return
     */
    List<ServerDto> selectByName(String name);

    /**
     * name的绝对查询，如果查询到多条将抛异常
     * @param name
     * @return
     */
    ServerDto selectOne(String name) throws Exception;

    /**
     * 主键查询
     * @param key
     * @return
     */
    ServerDto selectByPrimaryKey(Long key) throws Exception;

    boolean updateByKey(ServerDto serverDto) throws Exception;

}
