package com.itheima.hchat;

import com.itheima.hchat.utils.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author mou
 */
@MapperScan(basePackages = "com.itheima.hchat.mapper")
@SpringBootApplication
public class NettyChatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyChatServerApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(0,0);
    }

}
