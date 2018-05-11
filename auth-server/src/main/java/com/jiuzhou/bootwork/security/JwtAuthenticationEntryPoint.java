package com.jiuzhou.bootwork.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/28
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        //向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8,其实设置了本句，也默认设置了Response的编码方式为UTF-8，但是开发中最好两句结合起来使用
        httpServletResponse.setCharacterEncoding("UTF-8");

        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");

        httpServletResponse.setContentType("text/html;charset=UTF-8");

        PrintWriter writer = httpServletResponse.getWriter();

        writer.write("用户认证失败");

    }
}
