package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.service.dto.AppDto;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/27
 */
public interface AppService {

    Long insert(AppDto appDto) throws Exception;

    AppDto selectOneByNameUserId(String name, Long userId) throws Exception;

    boolean updateById(AppDto appDto) throws Exception;

    AppDto selectById(Long id) throws Exception;

    List<AppDto> selectByUserId(Long userId) throws Exception;

}
