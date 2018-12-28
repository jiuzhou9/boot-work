package sskj.local;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import sskj.local.tmp.Company;
import sskj.local.tmp.User;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class TestUnicorn {


    /**
     本地环境
     String url = "http://127.0.0.1:15102/eureka-api/api/v1/example/get?id=12";
     String appCode = "T4NPJ7UxsIjzszyS0g";
     String appToken = "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoiYWJj55qEQVBQIiwiZXhwIjoxNTI2NTQwODg3LCJ1c2VybmFtZSI6ImFiYyJ9.jcZh05IXPpe0fkEMuvCpoaBLUT5rC6MrV4hJHxJC9xRiODPcgpbEszqhq54JCWSAf8NF55Z7F_NinzuZ745TBw";

     测试环境
     String url = "http://47.94.134.37:15102/eureka-api/api/v1/example/get?id=12";
     String appCode = "6ZoNpHiRa3zaZyR3VS";
     String appToken = "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoi6JKZ54mb5Lmz5LiaQVBQIiwiZXhwIjoxNTI2NTM2OTMzLCJ1c2VybmFtZSI6IuiSmeeJm-S5s-S4miJ9.88TiGlvA_eoVemhB6u9-R4tJui4_yWuQKjH_9NLEkqU_Fkaa7_CSoUOtlrnwtE9GOftMXJGFaknD3SuIjZYVRg";

     post方式
     */
//    @Ignore
    @Test
    public void test_unicorn_api_testInput(){
        String key = "690da61e018a42e6a3e3b55bed84d4c8";
        String secret = "3176fde1c63c4a38b85cb4a9da439a8f";
        String keyToken = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjIxNDU4ODgwMDAsImtleSI6IjY5MGRhNjFlMDE4YTQyZTZhM2UzYjU1YmVkODRkNGM4In0.ZsWASJLiVX4EVxQORV2PghyDD-bu0seyWX57Gs8H5I4dBquCSp4Q9jmvLxcPC_AwQT_X1ebFf5LzaPTDgHlGTw";
        String url = "http://localhost:15102/unicorn-api/api/v1/test/testInput";


        Map<String, String> headers = new HashMap<>();
        //时间戳
        Long timestamp = System.currentTimeMillis();
        headers.put("reqTimestamp", Long.toString(timestamp));
        //签名：签名内容规则~   start
        User user = new User();
        user.setAge(12);
        user.setName("杉数");
        Company company = new Company();
        company.setAge(12);
        company.setName("杉数");
        company.setUser(user);
        List<User> list = new ArrayList<>();
        list.add(user);
        company.setList(list);

        StringBuffer sb = new StringBuffer();
        String s1 = JSON.toJSONString(company);
        System.err.println("请求body:: " + s1);
        JSONObject jsonObject = JSON.parseObject(s1);
        //key排序
        Set<String> keySet = jsonObject.keySet();
        List<String> keys = new ArrayList<>(keySet);
        Collections.sort(keys);
        System.err.println("按照第一层key进行排序。然后拼接。");
        for (String s : keys) {
            Object s2 = jsonObject.get(s);
            sb.append(s + "=" + s2 + "&");
        }

        // 2.结尾拼接时间戳
        sb.append(timestamp.toString());
        System.err.println("拼接后的待加密的明文:: " + sb.toString());

        // 3.转成大写，MD5加盐式加密
        String sign = MD5Util.MD5(sb.toString().toUpperCase() + secret);
        System.err.println("签名结果:: " + sign);
        //签名结束

        //添加签名信息
        headers.put("sign", sign);
        //添加key 令牌信息
        headers.put("x-access-token", keyToken);
        //添加key信息
        headers.put("key", key);

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000 * 3600);// 设置超时
        requestFactory.setReadTimeout(1000 * 3600);

        //创建rest 模板 发送请求
        RestTemplate restTemplate = new RestTemplate();
        CustomErrorHandler customErrorHandler = new CustomErrorHandler();
        restTemplate.setErrorHandler(customErrorHandler);
        restTemplate.setRequestFactory(requestFactory);

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

        HttpEntity<Company> entity = new HttpEntity<>(company, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        //json数据接收
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println(statusCode);
        String body = responseEntity.getBody();
        try {
            String s = new String(body.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void test_unicorn_api_result(){
        String key = "690da61e018a42e6a3e3b55bed84d4c8";
        String secret = "3176fde1c63c4a38b85cb4a9da439a8f";
        String keyToken = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjIxNDU4ODgwMDAsImtleSI6IjY5MGRhNjFlMDE4YTQyZTZhM2UzYjU1YmVkODRkNGM4In0.ZsWASJLiVX4EVxQORV2PghyDD-bu0seyWX57Gs8H5I4dBquCSp4Q9jmvLxcPC_AwQT_X1ebFf5LzaPTDgHlGTw";
        String url = "http://localhost:15102/unicorn-api/api/v1/optimization/result";

        Map<String, String> headers = new HashMap<>();
        //时间戳
        Long timestamp = System.currentTimeMillis();
        headers.put("reqTimestamp", Long.toString(timestamp));
        //签名：签名内容规则~   start
        User user = new User();
        user.setAge(12);
        user.setName("杉数");
        Company company = new Company();
        company.setAge(12);
        company.setName("杉数");
        company.setUser(user);
        List<User> list = new ArrayList<>();
        list.add(user);
        company.setList(list);

        StringBuffer sb = new StringBuffer();
        String s1 = JSON.toJSONString(company);
        System.err.println("请求body:: " + s1);
        JSONObject jsonObject = JSON.parseObject(s1);
        //key排序
        Set<String> keySet = jsonObject.keySet();
        List<String> keys = new ArrayList<>(keySet);
        Collections.sort(keys);
        System.err.println("按照第一层key进行排序。然后拼接。");
        for (String s : keys) {
            Object s2 = jsonObject.get(s);
            sb.append(s + "=" + s2 + "&");
        }

        // 2.结尾拼接时间戳
        sb.append(timestamp.toString());
        System.err.println("拼接后的待加密的明文:: " + sb.toString());

        // 3.转成大写，MD5加盐式加密
        String sign = MD5Util.MD5(sb.toString().toUpperCase() + secret);
        System.err.println("签名结果:: " + sign);
        //签名结束

        //添加签名信息
        headers.put("sign", sign);
        //添加key 令牌信息
        headers.put("x-access-token", keyToken);
        //添加key信息
        headers.put("key", key);

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000 * 3600);// 设置超时
        requestFactory.setReadTimeout(1000 * 3600);

        //创建rest 模板 发送请求
        RestTemplate restTemplate = new RestTemplate();
        CustomErrorHandler customErrorHandler = new CustomErrorHandler();
        restTemplate.setErrorHandler(customErrorHandler);
        restTemplate.setRequestFactory(requestFactory);

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

        HttpEntity<Company> entity = new HttpEntity<>(company, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        //json数据接收
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println(statusCode);
        String body = responseEntity.getBody();
        try {
            String s = new String(body.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
