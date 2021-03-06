package sskj.tmp;

import com.jiuzhou.bootwork.App;
import com.jiuzhou.bootwork.testpassword.MD5Util;
import com.jiuzhou.bootwork.testrest.CustomErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author wangjiuzhou (835540436@qq.com)
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
public class TestEureka2 {


    /**
     本地环境
     String url = "http://127.0.0.1:15102/eureka-api/api/v1/example/get?id=12";
     String appCode = "T4NPJ7UxsIjzszyS0g";
     String appToken = "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoiYWJj55qEQVBQIiwiZXhwIjoxNTI2NTQwODg3LCJ1c2VybmFtZSI6ImFiYyJ9.jcZh05IXPpe0fkEMuvCpoaBLUT5rC6MrV4hJHxJC9xRiODPcgpbEszqhq54JCWSAf8NF55Z7F_NinzuZ745TBw";

     测试环境
     String url = "http://47.94.134.37:15102/eureka-api/api/v1/example/get?id=12";
     String appCode = "6ZoNpHiRa3zaZyR3VS";
     String appToken = "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoi6JKZ54mb5Lmz5LiaQVBQIiwiZXhwIjoxNTI2NTM2OTMzLCJ1c2VybmFtZSI6IuiSmeeJm-S5s-S4miJ9.88TiGlvA_eoVemhB6u9-R4tJui4_yWuQKjH_9NLEkqU_Fkaa7_CSoUOtlrnwtE9GOftMXJGFaknD3SuIjZYVRg";

     */
//    @Ignore
    @Test
    public void test_sskj_eureka_api(){
        String key = "690da61e018a42e6a3e3b55bed84d4c8";
        String secret = "3176fde1c63c4a38b85cb4a9da439a8f";
        String keyToken = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjIxNDU4ODgwMDAsImtleSI6IjY5MGRhNjFlMDE4YTQyZTZhM2UzYjU1YmVkODRkNGM4In0.H4Oz7-ucgbVZePSwb-ErOCtHwsC3zQEDllQbSMEi8TdIMZzcG9BWtT7tpUJ7MEChZAFkZAlXaUyV_IxHbyQGKw";
        String url = "http://47.94.134.37:15102/eureka-api/api/v1/example/get?id=12";

//        String key = "341f4f6a637a4c65af55ba226c44ce46";
//        String secret = "ba7091c04ebf44808356b7eb9591c895";
//        String keyToken = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjIxNDU4ODgwMDAsImtleSI6IjM0MWY0ZjZhNjM3YTRjNjVhZjU1YmEyMjZjNDRjZTQ2In0.VnxQxodCMs3S6suRMzwsca-i3MLBGmab68dZ12eURQ3lYq4Z66g_W6zDP1sH3kn9IAJA5r7KwX3d1PgRob8rKQ";
//        String url = "http://127.0.0.1:15102/eureka-api/api/v1/example/get?id=12";

        Map<String, String> headers = new HashMap<>();
        //时间戳
        Long timestamp = System.currentTimeMillis();
        headers.put("reqTimestamp", Long.toString(timestamp));
        //签名：签名内容规则~   start
        // 1.URL中的参数按照键排序，然后&连接
        Map<String, Object> params = new HashMap<>();
        params.put("id", 12);

        List<String> keys = new ArrayList<>();
        params.forEach( (name, value) -> {
            keys.add(name);
        });

        Collections.sort(keys);

        StringBuffer sb = new StringBuffer();
        for (String name : keys) {
            sb.append(name + "=" + params.get(name) + "&");
        }
        // 2.结尾拼接时间戳
        sb.append(timestamp.toString());

        // 3.转成大写，MD5加盐式加密
        String sign = MD5Util.MD5(sb.toString().toUpperCase() + secret);
        //签名结束

        //添加签名信息
        headers.put("sign", sign);
        //添加key 令牌信息
        headers.put("x-access-token", keyToken);
        //添加key信息
        headers.put("key", key);

        //创建rest 模板 发送请求
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
                httpHeaders.add(next.getKey(), next.getValue());
            }
        }

        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        //json数据接收
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println(statusCode);
        String body = responseEntity.getBody();
        System.out.println(body);
    }


    @Test
    public void test(){
        String s = "3176fde1c63c4a38b85cb4a9da439a8f";
        int length = s.length();
        System.out.println(length);
    }
}
