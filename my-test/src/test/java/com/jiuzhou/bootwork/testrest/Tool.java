package com.jiuzhou.bootwork.testrest;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2017/11/14
 */
@Slf4j
@Deprecated
public class Tool {


    /**
     * 直接测试添然接口服务
     * restTemplate方式
     */
    @Test
    public void test_restTemplate_tianRan_suanfa(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sub_cate_id","化妆包");
        jsonObject.put("brand_id","ANTEPRIMA");
        jsonObject.put("usage_frac","70%");
        jsonObject.put("sex_en","men");
        jsonObject.put("purchase_price",2000f);
        jsonObject.put("goods_name","nike");
        jsonObject.put("cate_id","LV包");
        String url = "http://172.16.1.30:8004/predict";
        String s = restTemplatePostTool(jsonObject, url);
        System.out.println(s);
    }

    /**
     * get  请求
     * @param params Json参数
     * @param url URL
     * @return
     */
    public static String restTemplatePostTool(JSONObject params, String url){
        RestTemplate restTemplate = new RestTemplate();
        //自定义异常处理
        CustomErrorHandler customErrorHandler = new CustomErrorHandler();
        restTemplate.setErrorHandler(customErrorHandler);

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        HttpEntity<String> formEntity = new HttpEntity<String>(params.toString(), headers);
        String result = restTemplate.postForObject(url, formEntity, String.class);
        //处理异常
        HttpStatus statusCode = customErrorHandler.getStatusCode();
        if (statusCode != null){
            log.info("httpStatusCode（状态码）:"+statusCode.toString());
        }
        return result;
    }




    /**
     * get  请求
     * @param params Json参数
     * @param url URL
     * @param headerMap 头键值对
     * @return
     */
    public static String restTemplatePostTool(JSONObject params, String url, HashMap<String,String> headerMap){
        RestTemplate restTemplate = new RestTemplate();

        //自定义异常处理
        CustomErrorHandler customErrorHandler = new CustomErrorHandler();
        restTemplate.setErrorHandler(customErrorHandler);

        //头对象
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        if (!CollectionUtils.isEmpty(headerMap)){
            Iterator<Map.Entry<String, String>> iterator = headerMap.entrySet().iterator();

            while (iterator.hasNext()){
                Map.Entry<String, String> entry = iterator.next();
                String header = entry.getKey();
                String headerValue = entry.getValue();
                headers.add(header, headerValue);
            }
        }

        HttpEntity<String> formEntity = new HttpEntity<String>(params.toString(), headers);
        String result = restTemplate.postForObject(url, formEntity, String.class);

        //处理异常
        HttpStatus statusCode = customErrorHandler.getStatusCode();
        if (statusCode != null){
            log.info("httpStatusCode（状态码）:"+statusCode.toString());
        }
        return result;
    }



    /**
     * get请求认证服务器，校验用户令牌的正确性
     * get请求鉴权服务器，校验请求者的请求权限
     * @param headerHashMap header的键值对
     * @param url 请求URL
     * @return
     */
    public static String restTemplateGetTool(HashMap<String, String> headerHashMap, String url){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //自定义异常处理
        CustomErrorHandler customErrorHandler = new CustomErrorHandler();
        restTemplate.setErrorHandler(customErrorHandler);

        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        if (!CollectionUtils.isEmpty(headerHashMap)){
            Iterator<Map.Entry<String, String>> iterator = headerHashMap.entrySet().iterator();

            while (iterator.hasNext()){
                Map.Entry<String, String> entry = iterator.next();
                String header = entry.getKey();
                String headerValue = entry.getValue();
                headers.add(header, headerValue);
            }
        }
        HttpEntity<String> formEntity = new HttpEntity<>(headers);
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, formEntity, String.class);
//        String body = exchange.getBody();
//        return body;
        //处理异常
        HttpStatus statusCode = customErrorHandler.getStatusCode();
        if (statusCode != null){
            log.info(statusCode.toString());
        }
        return exchange.getBody();
    }
}

//class CustomErrorHandler extends DefaultResponseErrorHandler{
//
//    private HttpStatus statusCode;
//
//    public HttpStatus getStatusCode() {
//        return statusCode;
//    }
//
//    @Override
//    public void handleError(ClientHttpResponse response) throws IOException {
//        this.statusCode = getHttpStatusCode(response);
//    }
//
//    public HttpStatus getHttpStatusCode(ClientHttpResponse response) throws IOException {
//        HttpStatus statusCode;
//        try {
//            statusCode = response.getStatusCode();
//        }
//        catch (IllegalArgumentException ex) {
//            throw new UnknownHttpStatusCodeException(response.getRawStatusCode(),
//                                                     response.getStatusText(), response.getHeaders(), getResponseBody(response), getCharset(response));
//        }
//        return statusCode;
//    }
//
//
//    public byte[] getResponseBody(ClientHttpResponse response) {
//        try {
//            InputStream responseBody = response.getBody();
//            if (responseBody != null) {
//                return FileCopyUtils.copyToByteArray(responseBody);
//            }
//        }
//        catch (IOException ex) {
//            // ignore
//        }
//        return new byte[0];
//    }
//
//    public Charset getCharset(ClientHttpResponse response) {
//        HttpHeaders headers = response.getHeaders();
//        MediaType contentType = headers.getContentType();
//        return contentType != null ? contentType.getCharset() : null;
//    }
//}
