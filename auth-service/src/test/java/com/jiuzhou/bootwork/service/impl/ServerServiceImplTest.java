package com.jiuzhou.bootwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.AuthServiceApp;
import com.jiuzhou.bootwork.service.ServerService;
import com.jiuzhou.bootwork.service.dto.ServerDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/25
 */
@SpringBootTest(classes = AuthServiceApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class ServerServiceImplTest {

    @Autowired
    private ServerService serverService;

    @Test
    public void insert() {
        ServerDto serverDto = new ServerDto();
        serverDto.setName("eureka-api1");
        serverDto.setDescription("测试api");
        serverDto.setAvailable(true);
        ServerDto insert = null;
        try {
            insert = serverService.insert(serverDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(JSON.toJSONString(insert));
    }
}