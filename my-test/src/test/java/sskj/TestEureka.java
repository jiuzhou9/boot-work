package sskj;

import com.jiuzhou.bootwork.App;
import com.jiuzhou.bootwork.testrest.CustomErrorHandler;
import com.jiuzhou.bootwork.testrest.RequestTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Ignore;
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

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    @Ignore
    @Test
    public void test_sskj_eureka_api(){
//        String url = "http://127.0.0.1:15102/eureka-api/api/v1/example/get?id=12";
//        String appCode = "6xgMGQadx8cWPqZGdA";
//        String appToken = "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoi5rW36aOe5LidQVBQIiwiZXhwIjoxNTI3MDg4MTI2LCJ1c2VybmFtZSI6Iua1t-mjnuS4nSJ9.BYmMSotZd1AJgBfKFFYXMh2-cXhRInTVVcIlpYUvSjXk5nxf5k3WtsOPxJ2JsuPX8nJU4eyTnaUOEwRjpKGyxw";


        String url = "http://47.94.134.37:15102/eureka-api/api/v1/example/get?id=1";
        String appCode = "6ZoNpHiRa3zaZyR3VS";
        String appToken = "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoi6JKZ54mb5Lmz5LiaQVBQIiwiZXhwIjoxNTI3MDcyODgyLCJ1c2VybmFtZSI6IuiSmeeJm-S5s-S4miJ9.jRMgwl3PYpuXPbftGm7dgjPgGIVdfQUVon53fMp1Hutl2IbsyZNSpExwOdChrfWfnneEcTIaNjmqcgyHPv93jQ";

        Map<String, String> headers = new HashMap<>();
        //时间戳
        long timestamp = System.currentTimeMillis();
        headers.put("timestamp", Long.toString(timestamp));
        String clientValue = appCode + appToken + timestamp;
        //签名
        String clientAutograph = DigestUtils.sha1Hex(clientValue.getBytes(Charset.forName("UTF-8")));
        headers.put("autograph", clientAutograph);
        //APP 令牌
        headers.put("x-access-token", appToken);
        //APP code
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
        //json数据接收
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println(statusCode);
        String body = responseEntity.getBody();
        System.out.println(body);
//        return body;
    }

    /**
     * http 请求rest api 接口 此test 值能测试api 不能测试zuul
     */
//    @Test
    //    @Ignore
    public void test_eureka_api_tool(){
        String urlStr = "http://localhost:15102/eureka-api/api/v1/example/get?id=12";

        Map<String, String> headers = RequestTool.buildHeaders("T4NPJ7UxsIjzszyS0g",
                                                               "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoiYWJj55qEQVBQIiwiZXhwIjoxNTI2MTkwODg3LCJ1c2VybmFtZSI6ImFiYyJ9.UjRIkMEVzh2jwhnXJlpfCL9fHmgw4Tuu8NhVTVvVokML7dI7O5XGJqiFY8_zC4q37HHZNNxw0u7kJEwF5EFaPg", null);

        String responseEntity = RequestTool.getRequest(headers, urlStr, HttpMethod.GET);

        System.out.println(responseEntity);
    }

//    @Ignore
    @Test
    public void test_thread() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 30; i++) {
            final int index = i;
//            try {
//                Thread.sleep(index * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            cachedThreadPool.execute(new Runnable() {
                public void run() {
//                    System.out.println(index);
                    boolean b = test_sskj_eureka_api_for_thread_pool();
                    System.out.println(b);
                }
            });
        }

        try {
            Thread.sleep(1000 * 20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
    public boolean test_sskj_eureka_api_for_thread_pool(){
//        String url = "http://127.0.0.1:15102/eureka-api/api/v1/example/get?id=12";
//        String appCode = "6xgMGQadx8cWPqZGdA";
//        String appToken = "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoi5rW36aOe5LidQVBQIiwiZXhwIjoxNTI3MDg4MTI2LCJ1c2VybmFtZSI6Iua1t-mjnuS4nSJ9.BYmMSotZd1AJgBfKFFYXMh2-cXhRInTVVcIlpYUvSjXk5nxf5k3WtsOPxJ2JsuPX8nJU4eyTnaUOEwRjpKGyxw";


        String url = "http://47.94.134.37:15102/eureka-api/api/v1/example/get?id=1";
        String appCode = "6ZoNpHiRa3zaZyR3VS";
        String appToken = "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoi6JKZ54mb5Lmz5LiaQVBQIiwiZXhwIjoxNTI3MDcyODgyLCJ1c2VybmFtZSI6IuiSmeeJm-S5s-S4miJ9.jRMgwl3PYpuXPbftGm7dgjPgGIVdfQUVon53fMp1Hutl2IbsyZNSpExwOdChrfWfnneEcTIaNjmqcgyHPv93jQ";

        Map<String, String> headers = new HashMap<>();
        //时间戳
        long timestamp = System.currentTimeMillis();
        headers.put("timestamp", Long.toString(timestamp));
        String clientValue = appCode + appToken + timestamp;
        //签名
        String clientAutograph = DigestUtils.sha1Hex(clientValue.getBytes(Charset.forName("UTF-8")));
        headers.put("autograph", clientAutograph);
        //APP 令牌
        headers.put("x-access-token", appToken);
        //APP code
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
        //json数据接收
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.equals(HttpStatus.OK)){
            return true;
        }else {
            return false;
        }
    }
}
