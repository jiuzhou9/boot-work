package com.jiuzhou.bootwork.testrest;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.App;
import com.jiuzhou.bootwork.result.Result;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;


/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/15
 */
@Slf4j
@SpringBootTest(classes = App.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RestTest {


    /**
     * 返回值类型是json类型
     */
    @Test
    public void test_json(){

        HttpHeaders headers = new HttpHeaders();

        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.exchange("http://localhost:15021/api/v1/product/info?id=1", HttpMethod.GET.GET, formEntity, String.class);
        log.info(JSON.toJSONString(forEntity));
    }
}
