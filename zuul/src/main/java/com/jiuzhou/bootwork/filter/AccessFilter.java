package com.jiuzhou.bootwork.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.Map;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/23
 */
@Component
@Slf4j
public class AccessFilter extends ZuulFilter{

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
        Map<String, String> zuulRequestHeaders = currentContext.getZuulRequestHeaders();
        //        URL routeHost = currentContext.getRouteHost();
        HttpServletRequest request = currentContext.getRequest();
        log.info(JSON.toJSONString(request.getRemotePort()));
        log.info(JSON.toJSONString(request.getLocalPort()));
        log.info(JSON.toJSONString(request.getHeader("User-Agent")));
        log.info(JSON.toJSONString(zuulRequestHeaders));
        return null;
    }
}
