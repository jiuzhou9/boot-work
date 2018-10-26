package com.jiuzhou.bootwork;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.jiuzhou.bootwork.quartz.dao.mapper")
public class AuthServerApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(AuthServerApp.class, args);
    }
}
