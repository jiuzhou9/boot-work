package com.jiuzhou.bootwork;

import com.jiuzhou.bootwork.controller.HelloApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/02/09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RestApiApp.class)
@WebAppConfiguration
public class HelloApiTest {

    /**
     * 测试spring web
     */
    private MockMvc mvc;

    @Before
    public void init(){
        mvc = MockMvcBuilders.standaloneSetup(new HelloApi()).build();
    }

    @Test
    public void hello() throws Exception {
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON));
        //                        .andExpect(status().isOk()).andExpect(content().string(equalTo("hello world")));

        MockHttpServletResponse response = perform.andReturn().getResponse();
        System.out.println(response.getContentAsString());
    }
}