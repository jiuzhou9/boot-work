package com.jiuzhou.bootwork;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.constants.WhiteList;
import com.jiuzhou.bootwork.excep.ApiGateWayException;
import com.jiuzhou.bootwork.excep.HttpError;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.AuthService;
import com.jiuzhou.bootwork.service.dto.ApiRequestDTO;
import com.jiuzhou.bootwork.service.dto.AuthenticateResultDTO;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/04/13
 */
@Component
@Slf4j
public class AccessFilter extends ZuulFilter {

    @Autowired
    private AuthService authService;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        long l = System.currentTimeMillis();
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String apiPath = request.getServletPath();
        String method = request.getMethod();
        String keyToken = request.getHeader("x-access-token");
        String key = request.getHeader("key");
        String reqTimestamp = request.getHeader("reqTimestamp");
        String sign = request.getHeader("sign");
        Map<String, String[]> parameterMap = request.getParameterMap();
        String nonce = request.getHeader("nonce");

        log.info("REQUEST:: > " + request.getMethod() + " " + request.getRequestURI() + " " + request.getProtocol());

        Enumeration<String> headers = request.getHeaderNames();

        while (headers.hasMoreElements()) {
            String name = (String) headers.nextElement();
            String value = request.getHeader(name);
            log.info("REQUEST:: > " + name + ":" + value);
        }

        String body = null;
        if (!currentContext.isChunkedRequestBody()) {
            ServletInputStream inp = null;
            try {
                inp = request.getInputStream();
                if (inp != null) {
                    body = IOUtils.toString(inp, Charset.defaultCharset());
                    log.info("REQUEST:: > body:" + body);

                    /*BufferedReader reader = request.getReader();
                    String line = null;
                    StringBuilder sb = new StringBuilder();
                    while((line = reader.readLine())!=null){
                        sb.append(line);
                    }

                    // 将资料解码
                    String reqBody = sb.toString();
                    String decode = URLDecoder.decode(reqBody, HTTP.UTF_8);
                    log.info("REQUEST:: > body:" + decode);*/
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        boolean contain = WhiteList.contain(apiPath);
        if (contain){
            return null;

        }else {
            try {
                ApiRequestDTO apiRequestDTO = buidAuthenticateParameter(apiPath, key, keyToken, nonce, method,
                                                                        reqTimestamp, sign, parameterMap, body);
                AuthenticateResultDTO authenticate = authService.authenticate(apiRequestDTO);
                Integer companyId = authenticate.getCompanyId();
                try {
                    String requestId = URLEncoder.encode(authenticate.getRequestId(), "UTF-8");
                    String companyCode = URLEncoder.encode(authenticate.getCompanyCode(), "UTF-8");
                    String companyName = URLEncoder.encode(authenticate.getName(), "UTF-8");
                    String businessCode = URLEncoder.encode(authenticate.getBusinessCode(), "UTF-8");
                    key = URLEncoder.encode(authenticate.getKey(), "UTF-8");
                    String resourceCode = URLEncoder.encode(authenticate.getResourceCode(), "UTF-8");

                    currentContext.addZuulRequestHeader("companyId", String.valueOf(companyId));
                    currentContext.addZuulRequestHeader("callback", URLEncoder.encode(authenticate.getCallback().toString(), "UTF-8"));
                    currentContext.addZuulRequestHeader("requestId", requestId);
                    currentContext.addZuulRequestHeader("companyCode", companyCode);
                    currentContext.addZuulRequestHeader("companyName", companyName);
                    currentContext.addZuulRequestHeader("businessCode", businessCode);
                    if (authenticate.getCallback().equals(1)){
                        currentContext.addZuulRequestHeader("key", key);
                        currentContext.addZuulRequestHeader("resourceCode", resourceCode);
                    }
                    log.info("网关记录请求日志（待抽取)---> key:" + key + "resourceCode:" + resourceCode + "requestId:" + requestId );
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            } catch (ApiGateWayException e) {
                e.printStackTrace();
                returnFalse(currentContext, e.getHttpError());
                return currentContext;
            }
            log.info("网关认证耗时：：："+(System.currentTimeMillis() - l));
            log.info("网关校验完成--->路由api开始");
            return null;
        }
    }

    /**
     * 构建认证服务参数
     * @param apiPath
     * @param key
     * @param keyToken
     * @param nonce
     * @param method
     * @param reqTimestamp
     * @param sign
     * @param parameterMap
     * @return
     */
    @Deprecated
    private ApiRequestDTO buidAuthenticateParameter(String apiPath, String key, String keyToken, String nonce, String method, String reqTimestamp, String sign, Map<String, String[]> parameterMap) {
        ApiRequestDTO apiRequestDTO = new ApiRequestDTO();
        apiRequestDTO.setApiPath(apiPath);
        apiRequestDTO.setKey(key);
        apiRequestDTO.setKeyToken(keyToken);
        apiRequestDTO.setNonce(nonce);
        apiRequestDTO.setRequestMethod(method);
        apiRequestDTO.setReqTimestamp(Long.parseLong(reqTimestamp));
        apiRequestDTO.setSign(sign);
        apiRequestDTO.setParams(dealParameterMap(parameterMap));
        return apiRequestDTO;
    }

    /**
     * 构建认证服务参数
     * @param apiPath
     * @param key
     * @param keyToken
     * @param nonce
     * @param method
     * @param reqTimestamp
     * @param sign
     * @param parameterMap
     * @param body post等的请求中，参数是在body中的。
     * @return
     */
    private ApiRequestDTO buidAuthenticateParameter(String apiPath, String key, String keyToken, String nonce, String method, String reqTimestamp, String sign, Map<String, String[]> parameterMap, String body) {
        ApiRequestDTO apiRequestDTO = new ApiRequestDTO();
        apiRequestDTO.setApiPath(apiPath);
        apiRequestDTO.setKey(key);
        apiRequestDTO.setKeyToken(keyToken);
        apiRequestDTO.setNonce(nonce);
        apiRequestDTO.setRequestMethod(method);
        apiRequestDTO.setReqTimestamp(Long.parseLong(reqTimestamp));
        apiRequestDTO.setSign(sign);
        apiRequestDTO.setParams(dealParameterMap(parameterMap));
        apiRequestDTO.setBody(body);
        return apiRequestDTO;
    }

    /**
     * URL中的参数处理
     * @param parameterMap
     * @return
     */
    private Map<String, Object> dealParameterMap(Map<String, String[]> parameterMap){
        Map<String, Object> map = new HashMap<>();
        parameterMap.forEach((str, strArray) -> {
            map.put(str, strArray[0]);
        });
        return map;
    }

    /**
     * 异常处理
     * @param currentContext
     * @param httpError
     */
    private void returnFalse(RequestContext currentContext, HttpError httpError){
        currentContext.setResponseStatusCode(200);
        Result result = new Result();
        result.setHttpError(httpError);
        currentContext.setResponseBody(JSON.toJSONString(result));
        log.info(">>>>>>异常结果" + JSON.toJSONString(result));
        currentContext.setSendZuulResponse(false);
        //设置路由编码
        /*currentContext.getResponse().setContentType("text/html;charset=UTF-8");*/
        currentContext.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

}
