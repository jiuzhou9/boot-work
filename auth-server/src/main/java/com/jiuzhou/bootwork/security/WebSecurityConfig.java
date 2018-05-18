package com.jiuzhou.bootwork.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/28
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;

    //登录校验
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //    @Bean
    //    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
    //        return new JwtAuthenticationTokenFilter();
    //    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                        // 由于使用的是JWT，我们这里不需要csrf
                        .csrf().disable()

                        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()

                        // 基于token，所以不需要session
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                        .authorizeRequests()

                        // swagger 白名单
                        .antMatchers(AUTH_WHITELIST_SWAGGER).permitAll()

                        // 对于获取token、认证token、申请App等这些的rest api要允许匿名访问
                        .antMatchers(AUTH_WHITELIST_API).permitAll()

                        // 除上面外的所有请求全部需要鉴权认证
                        .anyRequest().authenticated();
        // 禁用缓存
        httpSecurity.headers().cacheControl();

        //FIXME 添加JWT filter 此Filter用于限制客户端直接请求，目前因为是路由请求本服务，所以这部分暂时相当于弃用
        //        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter
        // .class);

    }

    /**
     * api 白名单
     */
    private static final String[] AUTH_WHITELIST_API = {
                    "/api/v1/user/register",
                    "/api/v1/user/create-user-token",
                    "/api/v1/user/check-user-token",
                    "/api/v1/app/create",
                    "/api/v1/app/refresh",
                    "/api/v1/app/check"
    };

    /**
     * swagger 白名单
     */
    private static final String[] AUTH_WHITELIST_SWAGGER = {
                    // -- swagger ui
                    "/v2/api-docs",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**",
                    "/auth-server/swagger-ui.html"
                    // other public endpoints of your API may be appended to this array
    };
}
