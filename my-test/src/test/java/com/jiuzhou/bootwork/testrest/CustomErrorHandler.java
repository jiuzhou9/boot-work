package com.jiuzhou.bootwork.testrest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/04/08
 *
 * 自定义异常处理器
 */
public class CustomErrorHandler extends DefaultResponseErrorHandler {

    private HttpStatus statusCode;

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        this.statusCode = getHttpStatusCode(response);
    }

    public HttpStatus getHttpStatusCode(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode;
        try {
            statusCode = response.getStatusCode();
        }
        catch (IllegalArgumentException ex) {
            throw new UnknownHttpStatusCodeException(response.getRawStatusCode(),
                                                     response.getStatusText(), response.getHeaders(), getResponseBody(response), getCharset(response));
        }
        return statusCode;
    }


    public byte[] getResponseBody(ClientHttpResponse response) {
        try {
            InputStream responseBody = response.getBody();
            if (responseBody != null) {
                return FileCopyUtils.copyToByteArray(responseBody);
            }
        }
        catch (IOException ex) {
            // ignore
        }
        return new byte[0];
    }

    public Charset getCharset(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        MediaType contentType = headers.getContentType();
        return contentType != null ? contentType.getCharset() : null;
    }
}
