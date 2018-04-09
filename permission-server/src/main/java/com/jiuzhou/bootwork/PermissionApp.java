package com.jiuzhou.bootwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wangjiuzhou
 * @date
 * @enableScheduling 定时任务注解
 */
@EnableEurekaClient
@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class PermissionApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(PermissionApp.class, args);
    }
}
