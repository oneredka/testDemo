package com.hong.icarus.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.hong")
@MapperScan({"com.hong.biz.*.mapper", "com.hong.generator.mapper"})
@EnableTransactionManagement
public class IcarusWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(IcarusWebApplication.class, args);
    }

}
