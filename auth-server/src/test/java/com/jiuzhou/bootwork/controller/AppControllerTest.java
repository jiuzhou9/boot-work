package com.jiuzhou.bootwork.controller;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.AuthServerApp;
import com.jiuzhou.bootwork.controller.vo.AppTokenVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/04/09
 */
@SpringBootTest(classes = AuthServerApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Slf4j
public class AppControllerTest {
    /**
     * 测试spring web
     */
    private MockMvc mvc;

    @Before
    public void init(){
        mvc = MockMvcBuilders.standaloneSetup(new AppController()).build();
    }


    @Test
    public void checkToken() {
        try {
            ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/api/v1/app/check", new AppTokenVo()).accept(MediaType.APPLICATION_JSON));
            MvcResult mvcResult = resultActions.andReturn();
            String contentAsString = mvcResult.getResponse().getContentAsString();
            System.out.println(contentAsString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}