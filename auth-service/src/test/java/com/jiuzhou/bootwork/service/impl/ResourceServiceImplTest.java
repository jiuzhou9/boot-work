package com.jiuzhou.bootwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.AuthServiceApp;
import com.jiuzhou.bootwork.service.ResourceService;
import com.jiuzhou.bootwork.service.dto.ResourceDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/23
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthServiceApp.class)
public class ResourceServiceImplTest {

    @Autowired
    private ResourceService resourceService;

    @Test
    public void insert() {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setName("测试api1");
        resourceDto.setDescription("测试api");
        resourceDto.setType(RequestMethod.GET.name());
        resourceDto.setUrl("/api/v1/hello");
        resourceDto.setServerId(7L);
        try {
            Long insert = resourceService.insert(resourceDto);
            log.info(JSON.toJSONString(insert));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectOneByName() {
        ResourceDto resourceDto = null;
        try {
            resourceDto = resourceService.selectOneByName("测试api");
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(JSON.toJSONString(resourceDto));
    }

    @Test
    public void selectOneByUrl() {
        ResourceDto resourceDto = null;
        try {
            resourceDto = resourceService.selectOneByUrl("/api/v1/hello");
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(JSON.toJSONString(resourceDto));
    }

    @Test
    public void updateById() {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setDescription("测试更新");
        boolean b = false;
        try {
            b = resourceService.updateById(resourceDto, 1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(JSON.toJSONString(b));
    }

    @Test
    public void selectById() {
        try {
            ResourceDto resourceDto = resourceService.selectById(1L);
            log.info(JSON.toJSONString(resourceDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}