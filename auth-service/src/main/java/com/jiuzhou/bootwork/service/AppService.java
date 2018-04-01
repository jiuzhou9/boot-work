package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.dto.AppDto;
import com.jiuzhou.bootwork.service.dto.AppTokenDto;


/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/27
 */
public interface AppService {

    //    出于安全考虑接口不提供以下方法的暴露----start
    //    AppDto selectOneByNameUserId(String name, Long userId) throws ServiceException;
    //
    //    boolean updateById(AppDto appDto) throws ServiceException;
    //
    //    List<AppDto> selectByUserId(Long userId) throws ServiceException;
    //    出于安全考虑接口不提供以下方法的暴露----end

    /**
     * 凭借用户的令牌创建APP，返回APP令牌
     * @param userToken
     * @param appName
     * @return
     */
    AppTokenDto insert(String userToken, String appName) throws ServiceException;

    /**
     * 根据用户令牌、APPname获取APP令牌，每次获取新的app令牌将会自动刷新secret，因此任何时刻只能有唯一的APP令牌是正确的
     * @param userToken
     * @param appName
     * @return
     */
    AppTokenDto getAppToken(String userToken, String appName) throws ServiceException;

    /**
     * 校验APP 令牌，返回APP name，user name信息，一旦过期，抛异常请用户重新刷新
     * @param appToken
     * @param code
     * @return
     * @throws ServiceException
     */
    AppTokenDto checkAppToken(String appToken, String code) throws ServiceException;

    AppDto selectById(Long id) throws ServiceException;

    boolean decide(AppTokenDto appTokenDto) throws ServiceException;
}
