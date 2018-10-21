package com.jiuzhou.bootwork.controller.security;

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
 * @date 2018/05/16
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                        .csrf().disable()

                        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()

                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                        .authorizeRequests()

                        .antMatchers(AUTH_WHITELIST_SWAGGER).permitAll()

                        .antMatchers(AUTH_WHITELIST_API).permitAll()

                        .anyRequest().authenticated();
        httpSecurity.headers().cacheControl();

    }

    /**
     * api 白名单
     */
    private static final String[] AUTH_WHITELIST_API = {
                    "/api/v1/access-key/authenticate",
                    "/api/v1/access-key/update-quota",
                    "/api/v1/access-key/init-cache",
                    "/api/v1/syn-cache/**",
//                    "/job/**",
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
                    "/auth-server/swagger-ui.html",

//                    "/JobManager.html"
                    // other public endpoints of your API may be appended to this array
    };
}
