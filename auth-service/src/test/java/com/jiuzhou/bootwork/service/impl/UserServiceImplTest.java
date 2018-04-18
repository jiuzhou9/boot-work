package com.jiuzhou.bootwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.AuthServiceApp;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.UserService;
import com.jiuzhou.bootwork.service.dto.UserDto;
import com.jiuzhou.bootwork.service.dto.UserTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@SpringBootTest(classes = AuthServiceApp.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void register() {
        UserDto userDto = new UserDto();
        userDto.setMobile("12345678999");
        userDto.setUsername("abc");
        userDto.setPassword(userDto.getUsername());
        try {
            UserTokenDto userTokenDto = userService.register(userDto);
            log.info(JSON.toJSONString(userTokenDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectOneAvailableWithRolesByUsername() {
        UserDto userDto = null;
        try {
            userDto = userService.selectOneAvailableWithRolesByUsername("蒙牛乳业");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        log.info(JSON.toJSONString(userDto));
    }
}