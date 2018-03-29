package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.dto.AppDto;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/27
 */
public interface AppService {

    Long insert(AppDto appDto) throws ServiceException;

    AppDto selectOneByNameUserId(String name, Long userId) throws ServiceException;

    boolean updateById(AppDto appDto) throws ServiceException;

    AppDto selectById(Long id) throws ServiceException;

    List<AppDto> selectByUserId(Long userId) throws ServiceException;

}
