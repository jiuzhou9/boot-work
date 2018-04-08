package com.jiuzhou.bootwork.testrest;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.App;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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
     * @Ignore 打包自动跳过Test
     */
    @Ignore
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

    /**
     * http 请求rest api 接口 此test 值能测试api 不能测试zuul
     */
    @Test
//    @Ignore
    public void test_eureka_api(){
        String urlStr = "http://localhost:15040/eureka-api/api/v1/hello";

        Map<String, String> headers = RequestTool.buildHeaders("BZNTKomAhZwY3EU9iD",
                                                   "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoi5rWL6K-VQVBQIiwiZXhwIjoxNTI1MTAzOTI4fQ.eTDzilG7N1b_vN4F7zrWneppRuxTDCoPWwv_vlYjshb3iaRDeZ2f4h0XVFyejEdPa_UkT1rXPWwO_OuQcZSGWg", null);

        String responseEntity = RequestTool.getRequest(headers, urlStr, HttpMethod.GET);

        System.out.println(responseEntity);
    }


}
