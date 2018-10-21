package com.jiuzhou.bootwork.service;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/04/01
 */
public interface AuthService {

    /**
     * 校验时间戳是否合法，校验签名是否合法
     * @param timestamp 时间戳
     * @param autograph 签名
     * @param appCode   APP code
     * @param appToken  APP 令牌
     * @return
     * @throws ServiceException
     */
    boolean checkTimestampAndAutograph(String timestamp, String autograph, String appCode, String appToken) throws ServiceException;

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
