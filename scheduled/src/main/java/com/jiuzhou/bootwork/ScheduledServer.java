package com.jiuzhou.bootwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 * @author wangjiuzhou
 */
@SpringBootApplication
@EnableScheduling
public class ScheduledServer
{
    public static void main( String[] args )
    {
        SpringApplication.run(ScheduledServer.class, args);
    }
}
