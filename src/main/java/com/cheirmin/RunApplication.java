package com.cheirmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 16:00
 * @Version 1.0
 */

@MapperScan("com.cheirmin.dao")
@SpringBootApplication(scanBasePackages = {"com.cheirmin"})
public class RunApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class,args);
    }
}
