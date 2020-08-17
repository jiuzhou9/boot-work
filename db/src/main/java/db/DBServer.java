package db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2020/06/24
 */
@SpringBootApplication
@EnableScheduling
public class DBServer {

    public static void main(String[] args) {
        SpringApplication.run(DBServer.class, args);
    }
}
