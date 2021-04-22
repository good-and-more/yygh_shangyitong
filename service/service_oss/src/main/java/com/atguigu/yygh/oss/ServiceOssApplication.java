package com.atguigu.yygh.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//因为在该模块中不操作数据库
public class ServiceOssApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOssApplication.class, args);
    }
}
