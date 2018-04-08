package com.jiuzhou.bootwork.testrest;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
 * @date 2018/04/08
 */
public class RequestTool {
    /**
     * get 请求
     * @param headers
     * @param urlStr
     * @param httpMethod
     * @return
     */
    public static String getRequest(Map<String, String> headers, String urlStr, HttpMethod httpMethod){
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
        List<Charset> charsets = new ArrayList<>();
        httpHeaders.setAcceptCharset(charsets);

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
        ResponseEntity<String> responseEntity = restTemplate.exchange(urlStr, httpMethod, entity, String.class);

        //        try {
        //            String s1 = responseEntity.toString();
        //            System.out.println(new String(s1.getBytes(), Charset.forName("UTF-8")));
        //
        //            String body = responseEntity.getBody();
        //            System.out.println(body);
        //
        //            String utf8 = new String(body.getBytes( "UTF-8"));
        //            System.out.println(utf8);
        //
        //            String decode = URLDecoder.decode(body, "UTF-8");
        //            System.out.println(decode);
        //        } catch (UnsupportedEncodingException e) {
        //            e.printStackTrace();
        //        }

        return JSON.toJSONString(responseEntity);
    }

    /**
     * 封装map 返回时间戳、签名、token、code
     * @param appCode
     * @param appToken
     * @param headers 允许为空
     * @return
     */
    public static Map<String, String> buildHeaders(String appCode, String appToken, Map<String, String> headers){
        if (headers == null){
            headers = new HashMap<>();
        }
        long timestamp = System.currentTimeMillis();
        headers.put("timestamp", Long.toString(timestamp));
        String clientValue = appCode + appToken + timestamp;
        String clientAutograph = DigestUtils.sha1Hex(clientValue.getBytes(Charset.forName("UTF-8")));
        headers.put("autograph", clientAutograph);
        headers.put("x-access-token", appToken);
        headers.put("code", appCode);

        return headers;
    }
}
