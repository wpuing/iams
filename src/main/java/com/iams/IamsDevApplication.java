package com.iams;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling//开启定时
@MapperScan("com.iams.core.mapper")
public class IamsDevApplication {

    public static void main(String[] args) {
        SpringApplication.run(IamsDevApplication.class, args);
    }

}
