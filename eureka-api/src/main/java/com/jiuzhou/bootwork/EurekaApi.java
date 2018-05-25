package com.jiuzhou.bootwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@SpringBootApplication
//启动eureka客户端
@EnableEurekaClient
//feign客户端
@EnableFeignClients
public class EurekaApi
{
    public static void main( String[] args )
    {
        SpringApplication.run(EurekaApi.class, args);
    }
}
