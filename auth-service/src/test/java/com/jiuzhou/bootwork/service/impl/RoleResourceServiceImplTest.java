package com.jiuzhou.bootwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.AuthServiceApp;
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
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
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

}