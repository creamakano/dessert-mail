package com.dessert.mail.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.multipart.MultipartFile;

@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
@EnableDiscoveryClient
@EnableRedisHttpSession
@MapperScan({"com.dessert.mail.product.mapper"})
@EnableFeignClients
public class MailProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailProductApplication.class, args);
    }

}
