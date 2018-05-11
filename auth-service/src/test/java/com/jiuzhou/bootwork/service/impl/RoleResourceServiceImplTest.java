package com.jiuzhou.bootwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.AuthServiceApp;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.RoleResourceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/04/01
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthServiceApp.class)
public class RoleResourceServiceImplTest {

    @Autowired
    private RoleResourceService roleResourceService;
    @Test
    public void loadPermission() {
        roleResourceService.loadPermission();
    }

    @Test
    public void getAttributes() {
        Collection<String> get = roleResourceService.getAttributes("/eureka-api/api/v1/hello/123/name", "GET");
        log.info(JSON.toJSONString(get));
    }

    @Test
    public void decide() {
        try {
            boolean decide = roleResourceService.decide("123", "/eureka-api/api/v1/hello/12/name", "测试APP", "POST");
            log.info(JSON.toJSONString(decide));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void decideWithPay() {
        try {
            boolean decide = roleResourceService.decideWithPay("蒙牛乳业", "/eureka-api/api/v1/example/get?id=12", "蒙牛APP", "GET");
            log.info(JSON.toJSONString(decide));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}