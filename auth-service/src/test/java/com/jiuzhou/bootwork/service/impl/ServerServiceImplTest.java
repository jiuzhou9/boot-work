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

import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
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
        serverDto.setName("eureka-api3");
        serverDto.setDescription("测试api");
        serverDto.setAvailable(true);
        Long insert = null;
        try {
            insert = serverService.insert(serverDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(JSON.toJSONString(insert));
    }

    @Test
    public void selectByName() {
        List<ServerDto> serverDtos = serverService.selectLikeName("eureka-api");
        log.info(JSON.toJSONString(serverDtos));
    }

    @Test
    public void selectOne() {
        try {
            ServerDto serverDto = serverService.selectOneByName("eureka-api");
            log.info(JSON.toJSONString(serverDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectByPrimaryKey() {
        ServerDto serverDto = null;
        try {
            serverDto = serverService.selectByPrimaryKey(7L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(JSON.toJSONString(serverDto));
    }

    @Test
    public void updateByKey() {
        ServerDto serverDto = new ServerDto();
        serverDto.setId(7L);
        serverDto.setAvailable(false);
        boolean b = false;
        try {
            b = serverService.updateByKey(serverDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(JSON.toJSONString(b));
    }
}