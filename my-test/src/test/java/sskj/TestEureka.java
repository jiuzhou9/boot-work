package sskj;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.App;
import com.jiuzhou.bootwork.testrest.CustomErrorHandler;
import com.jiuzhou.bootwork.testrest.RequestTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/04/13
 */

//datasource:
//    driver-class-name: com.mysql.jdbc.Driver
//    url: jdbc:mysql://localhost:3306/20180401?useUnicode=true&characterEncoding=utf8
//    username: root
//    password: 12345
@Slf4j
@SpringBootTest(classes = App.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestEureka {

    @Test
    public void test_sskj_eureka_api(){
        String url = "http://localhost:15102/eureka-api/api/v1/example/get?id=12";
        String appCode = "T4NPJ7UxsIjzszyS0g";
        String appToken = "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoiYWJj55qEQVBQIiwiZXhwIjoxNTI2MTkwODg3LCJ1c2VybmFtZSI6ImFiYyJ9.UjRIkMEVzh2jwhnXJlpfCL9fHmgw4Tuu8NhVTVvVokML7dI7O5XGJqiFY8_zC4q37HHZNNxw0u7kJEwF5EFaPg";

        Map<String, String> headers = new HashMap<>();
        long timestamp = System.currentTimeMillis();
        headers.put("timestamp", Long.toString(timestamp));
        String clientValue = appCode + appToken + timestamp;
        String clientAutograph = DigestUtils.sha1Hex(clientValue.getBytes(Charset.forName("UTF-8")));
        headers.put("autograph", clientAutograph);
        headers.put("x-access-token", appToken);
        headers.put("code", appCode);


        //创建rest 模板
        RestTemplate restTemplate = new RestTemplate();
        CustomErrorHandler customErrorHandler = new CustomErrorHandler();
        restTemplate.setErrorHandler(customErrorHandler);
        //创建HTTP header
        HttpHeaders httpHeaders = new HttpHeaders();
        //定义内容类型
        MediaType mediaType = MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpHeaders.setContentType(mediaType);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypes);

        if (!CollectionUtils.isEmpty(headers)){
            //处理HTTP header
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                httpHeaders.add(key, value);
            }
        }

        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println(JSON.toJSONString(responseEntity));
    }

    /**
     * http 请求rest api 接口 此test 值能测试api 不能测试zuul
     */
    @Test
    //    @Ignore
    public void test_eureka_api_tool(){
        String urlStr = "http://localhost:15102/eureka-api/api/v1/example/get?id=12";

        Map<String, String> headers = RequestTool.buildHeaders("T4NPJ7UxsIjzszyS0g",
                                                               "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoiYWJj55qEQVBQIiwiZXhwIjoxNTI2MTkwODg3LCJ1c2VybmFtZSI6ImFiYyJ9.UjRIkMEVzh2jwhnXJlpfCL9fHmgw4Tuu8NhVTVvVokML7dI7O5XGJqiFY8_zC4q37HHZNNxw0u7kJEwF5EFaPg", null);

        String responseEntity = RequestTool.getRequest(headers, urlStr, HttpMethod.GET);

        System.out.println(responseEntity);
    }
}
