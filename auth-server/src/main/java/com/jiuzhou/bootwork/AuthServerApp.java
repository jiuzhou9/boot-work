package com.jiuzhou.bootwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableSwagger2
public class AuthServerApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(AuthServerApp.class, args);
    }
}
