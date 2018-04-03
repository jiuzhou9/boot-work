package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.excep.ServiceException;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/04/01
 */
public interface AuthService {

    /**
     * 校验身份正确性，权限
     * @param appToken
     * @param code
     * @param serverResource
     * @param method
     * @return
     */
    boolean checkAuthAndPermission(String appToken, String code, String serverResource, String method) throws ServiceException;
}
