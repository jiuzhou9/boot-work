package com.jiuzhou.bootwork.filter;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.constants.WhiteList;
import com.jiuzhou.bootwork.excep.HttpError;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.AuthService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        return "pre";
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
        String appToken = request.getHeader("x-access-token");
        String code = request.getHeader("code");

        boolean contain = WhiteList.contain(servletPath);

        if (contain){
            return null;
        }else {
            //检测如果是其他那么需要校验身份信息正确性
            //校验权限
            boolean b = false;
            try {
                b = authService.checkAuthAndPermission(appToken, code);
            } catch (ServiceException e) {
                e.printStackTrace();
                HttpError httpError = e.getHttpError();
                int value = httpError.getHttpStatus().value();
                currentContext.setResponseStatusCode(value);
                Result result = new Result();
                result.setHttpError(e.getHttpError());
                currentContext.setResponseBody(JSON.toJSONString(result));
                currentContext.setSendZuulResponse(false);
                return null;
            }
        }



        //
//        Map<String, String> zuulRequestHeaders = currentContext.getZuulRequestHeaders();
//        //        URL routeHost = currentContext.getRouteHost();
//
//        log.info(JSON.toJSONString(request.getRemotePort()));
//        log.info(JSON.toJSONString(request.getLocalPort()));
//        log.info(JSON.toJSONString(request.getHeader("User-Agent")));
//        log.info(JSON.toJSONString(zuulRequestHeaders));
        return null;
    }
}
