package com.jiuzhou.bootwork;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan(basePackages = "com.jiuzhou.bootwork.dao.mapper")
public class AuthServiceApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(AuthServiceApp.class, args);
    }
}
