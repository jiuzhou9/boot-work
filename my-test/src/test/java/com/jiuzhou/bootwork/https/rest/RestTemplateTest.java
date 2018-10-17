package com.jiuzhou.bootwork.https.rest;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/25
 *
 * restTemplate https
 */
public class RestTemplateTest {

    private String url = "https://140.143.140.201:8065/TMS_UAT/v1/NoticeResultServlet";

    @Bean
    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        /**
         * 指定 host 验证
         */
        HostnameVerifier nullHostnameVerifier = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };


        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                        .loadTrustMaterial(null, acceptingTrustStrategy)
                        .build();

        //ip 地址会报错
//        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        //解决IP地址会报错问题
        //org.apache.http.conn.ssl.SSLConnectionSocketFactory.SSLConnectionSocketFactory(javax.net.ssl.SSLContext, javax.net.ssl.HostnameVerifier)
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, nullHostnameVerifier);

        CloseableHttpClient httpClient = HttpClients.custom()
                        .setSSLSocketFactory(csf)
                        .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                        new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    /**
     * success
     */
    @Test
    public void testHttps(){
        String url = "https://free-api.heweather.com/v5/forecast?city=CN101080101&key=5c043b56de9f4371b0c7f8bee8f5b75e";
        String resp = null;
        try {
            resp = restTemplate().getForObject(url, String.class);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        System.out.println(resp);
    }

    /**
     * success ok
     */
    @Test
    public void testZWY(){

        ResponseEntity<String> resp = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("KeyId", "7b023527-32a4-49bd-87bd-5d5a1ccee199");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        ZwyNotice zwyNotice = new ZwyNotice();
        zwyNotice.setRequestId("geo-20180926000002");
        HttpEntity<ZwyNotice> request = new HttpEntity<>(zwyNotice, httpHeaders);

        System.out.println("请求request：" + JSON.toJSONString(request));
        try {
            resp = restTemplate().postForEntity(url, request, String.class);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        System.out.println("响应状态码" + resp.getStatusCode());
    }


    @Test
    public void testZwy2(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("KeyId", "7b023527-32a4-49bd-87bd-5d5a1ccee199");

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("requestId", "geo-20180926000001");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        System.out.println("请求体" + JSON.toJSONString(request));
        ResponseEntity<String> response = null;
        try {
            response = restTemplate().postForEntity( url, request , String.class );
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        System.out.println("响应状态码" + response.getStatusCode());
    }

}

@Data
class ZwyNotice {
    private String requestId;
}
