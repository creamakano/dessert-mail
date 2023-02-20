package com.dessert.mail.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisHttpSession
@MapperScan({"com.dessert.mail.product.mapper"})
public class MailProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailProductApplication.class, args);
    }

}
