package com.dessert.mail.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisHttpSession
@MapperScan({"com.dessert.mail.user.mapper"})
public class MailUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailUserApplication.class, args);
    }

}
