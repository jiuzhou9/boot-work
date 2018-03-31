package com.jiuzhou.bootwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Hello world!
 *
 */
@EnableZuulProxy
@SpringBootApplication
@EnableFeignClients
public class ZuulApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(ZuulApp.class, args);
    }
}
