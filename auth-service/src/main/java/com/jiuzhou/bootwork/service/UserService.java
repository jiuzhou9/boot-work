package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.dto.UserDto;
import com.jiuzhou.bootwork.service.dto.UserTokenDto;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/27
 */
public interface UserService {

    //    出于安全考虑接口不提供以下方法的暴露----start
    //    Long insert(UserDto userDto) throws ServiceException;
    //
    //    boolean updateById(UserDto userDto) throws ServiceException;
    //
    //    出于安全考虑接口不提供以下方法的暴露----end

    UserTokenDto register(UserDto userDto) throws ServiceException;

    UserTokenDto login(String username, String password) throws ServiceException;

    /**
     * 校验userToken是否是可用的，如果token正确，但是过期，那么用户重新刷新；
     * 如果token不正确那么抛异常；
     * 如果用户失效，那么抛异常，提示用户已经失效
     * @return
     * @throws ServiceException
     * @param userToken
     */
    UserDto checkUserToken(String userToken) throws ServiceException;

    /**
     * @param id
     * @return
     * @throws ServiceException
     */
    UserDto selectById(Long id) throws ServiceException;

    UserDto selectOneByUsername(String username) throws ServiceException;

    UserDto selectOneByMobile(String mobile) throws ServiceException;

    /**
     * 查询用户信息
     * @param username 用户名
     * @return 有效的用户，并且有该用户的角色信息
     * @throws ServiceException
     */
    UserDto selectOneAvailableWithRolesByUsername(String username) throws ServiceException;
}
