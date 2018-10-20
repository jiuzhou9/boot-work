/*
 * Copyright (c) 2017-2018, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.service.dto.UserDTO;
import com.jiuzhou.bootwork.service.dto.UserLoginDTO;
import com.jiuzhou.bootwork.excep_new.ApiGateWayException;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/15
 */
public interface UserService {

    /**
     * 用户注册
     * @param userDTO 用户注册信息对象
     * @return 用户ID
     * @throws ApiGateWayException
     */
    Integer register(UserDTO userDTO) throws ApiGateWayException;

    /**
     * 用户登录
     * @param userLoginDTO 用户登录对象，包含：用户名/用户邮箱/用户手机号(非空)、用户密码信息（非空）
     * @return 用户id
     */
    Integer login(UserLoginDTO userLoginDTO) throws ApiGateWayException;

    /**
     * 用户条件查询
     * @param userCode 查询条件userCode
     * @return 返回唯一一个用户
     */
    UserDTO getByUserCode(String userCode) throws ApiGateWayException;

    /**
     * ID查询
     * @param userId
     * @return
     */
    UserDTO getById(Integer userId);

    /**
     * 返回一个有效的用户
     * @param userId
     * @return
     */
    UserDTO getAvailableUserById(Integer userId);

    /**
     * 更新用户公司编码
     * @param userId
     * @param companyCode
     * @return
     * @throws ApiGateWayException
     */
    boolean updateUserCompanyCode(Integer userId, String companyCode) throws ApiGateWayException;

}
