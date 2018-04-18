package com.jiuzhou.bootwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.AuthServiceApp;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.UserRoleService;
import com.jiuzhou.bootwork.service.dto.RoleDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/04/18
 */
@SpringBootTest(classes = AuthServiceApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class UserRoleServiceImplTest {

    @Autowired
    private UserRoleService userRoleService;

    @Test
    public void selectAvailableByUserId() {
        List<RoleDto> roleDtos = null;
        try {
            roleDtos = userRoleService.selectAvailableByUserId(1L);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        log.info(JSON.toJSONString(roleDtos));
    }
}