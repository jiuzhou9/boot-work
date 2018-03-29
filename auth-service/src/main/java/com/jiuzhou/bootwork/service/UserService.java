package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.dto.UserDto;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/27
 */
public interface UserService {

    Long insert(UserDto userDto) throws ServiceException;

    UserDto selectOneByUsername(String username) throws ServiceException;

    boolean updateById(UserDto userDto) throws ServiceException;

    UserDto selectOneByMobile(String mobile) throws ServiceException;

    UserDto selectById(Long id) throws ServiceException;

    Long register(UserDto userDto) throws ServiceException;

}
