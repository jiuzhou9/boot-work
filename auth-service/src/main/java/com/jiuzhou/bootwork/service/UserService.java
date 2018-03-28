package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.service.dto.UserDto;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/27
 */
public interface UserService {

    Long insert(UserDto userDto) throws Exception;

    UserDto selectOneByUsername(String username) throws Exception;

    boolean updateById(UserDto userDto) throws Exception;

    UserDto selectOneByMobile(String mobile) throws Exception;

    UserDto selectById(Long id) throws Exception;

    Long register(UserDto userDto) throws Exception;

}
