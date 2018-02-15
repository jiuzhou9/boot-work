package com.jiuzhou.bootwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Hello world!
 * @author wangjiuzhou
 */
@SpringBootApplication //此注解可以不使用
@EnableEurekaServer
public class EurekaServer
{
    public static void main( String[] args )
    {
        SpringApplication.run(EurekaServer.class, args);
    }
}
