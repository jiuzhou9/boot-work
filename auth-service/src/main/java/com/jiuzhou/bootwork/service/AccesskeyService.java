/*
 * Copyright (c) 2017-2018, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.service.dto.AccesskeyDTO;
import com.jiuzhou.bootwork.service.dto.AccesskeyTokenDTO;
import com.jiuzhou.bootwork.service.dto.ApiRequestDTO;
import com.jiuzhou.bootwork.service.dto.AuthenticateResultDTO;
import com.jiuzhou.bootwork.excep_new.ApiGateWayException;

import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/17
 */
public interface AccesskeyService {

    /**
     * 创建AccessKey
     * 1.校验key的重复性
     * 2.校验companyCode的正确性
     * 3.校验userCode的正确性
     * 4.生成accessKey
     * 5.生成privateSecret保证唯一性 32位UUID+3随机字符串
     * 6.Redis缓存accessKey信息
     *
     * 插入成功后会生成对应的一个令牌，相当于key(app)对应的令牌
     *
     * @param keyName 用户创建的key名字
     * @param userId 用户ID
     * @return accessKey的令牌
     * @throws ApiGateWayException 非空、编码是否正确等多种校验，校验失败抛异常
     */
    AccesskeyTokenDTO create(String keyName, Integer userId) throws ApiGateWayException;

    /**
     * 认证一个请求的许可性，如果认证成功，返回请求者和请求资源信息
     * 1.key的有效性,时间戳的合法性
     * 2.签名的合法性
     * 3.令牌的合法性，是否过期，内容是否正确
     * 4.校验api路径是否合法
     * 4.额度的合法性
     *
     * @param apiRequestDTO 请求信息
     * @return AuthenticateResultDTO 请求者、请求资源信息
     */
    AuthenticateResultDTO authenticate(ApiRequestDTO apiRequestDTO) throws ApiGateWayException;

    /**
     * 根据用户ID、key名字获取新的key令牌
     * 需要做以下工作：
     * 1.校验用户可用性
     * 2.校验用户所在公司可用性
     * 3.校验所在公司key的可用性
     * 4.生成新的privateSecret，并更新到AccessKey表和Redis中
     * 4.生成key的新令牌，以前的令牌全部作废
     *
     * @param keyName
     * @param userId
     * @return
     */
    AccesskeyTokenDTO getNewToken(String keyName, Integer userId) throws ApiGateWayException;


    /**
     * 获取全部的accessKey
     * @return
     */
    List<AccesskeyDTO> getAllAvailable();
}
