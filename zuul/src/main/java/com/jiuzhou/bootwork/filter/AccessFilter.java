package com.jiuzhou.bootwork.filter;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.constants.WhiteList;
import com.jiuzhou.bootwork.excep.HttpError;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.AuthService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/23
 */
@Component
@Slf4j
public class AccessFilter extends ZuulFilter{

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
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String servletPath = request.getServletPath();
        String method = request.getMethod();
        String appToken = request.getHeader("x-access-token");
        String code = request.getHeader("code");
        String timestamp = request.getHeader("timestamp");
        String autograph = request.getHeader("autograph");

        boolean contain = WhiteList.contain(servletPath);

        if (contain){
            return null;
        }else {
            try {
                //校验时间戳、签名
                authService.checkTimestampAndAutograph(timestamp, autograph, code, appToken);
                authService.checkAuthAndPermission(appToken, code, servletPath, method);
            } catch (ServiceException e) {
                e.printStackTrace();
                returnFalse(currentContext, e.getHttpError());
                return currentContext;
            }
            return null;
        }
    }

    /**
     * 异常处理
     * @param currentContext
     * @param httpError
     */
    private void returnFalse(RequestContext currentContext, HttpError httpError){
        currentContext.setResponseStatusCode(httpError.getHttpStatus().value());
        Result result = new Result();
        result.setHttpError(httpError);
        currentContext.setResponseBody(JSON.toJSONString(result));
        currentContext.setSendZuulResponse(false);
        //设置路由编码
//        currentContext.getResponse().setContentType("text/html;charset=UTF-8");
        currentContext.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

}
