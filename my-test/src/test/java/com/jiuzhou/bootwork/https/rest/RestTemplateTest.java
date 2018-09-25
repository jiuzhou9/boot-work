package com.jiuzhou.bootwork.https.rest;

import lombok.Data;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/09/25
 *
 * restTemplate https
 */
public class RestTemplateTest {

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

    @Test
    public void testZWY(){
        String url = "https://140.143.140.201:8065/TMS_UAT/v1/NoticeResultServlet";
        ResponseEntity<String> resp = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("KeyId", "7b023527-32a4-49bd-87bd-5d5a1ccee199");
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(httpHeaders);

        ZwyNotice zwyNotice = new ZwyNotice();
        zwyNotice.setRequestId("1234567");

        try {
            resp = restTemplate().postForEntity(url, stringHttpEntity, String.class, zwyNotice);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        System.out.println(resp.getStatusCode());
    }

}

@Data
class ZwyNotice {
    private String requestId;
}
