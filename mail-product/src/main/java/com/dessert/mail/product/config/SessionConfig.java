package com.dessert.mail.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class SessionConfig {

    //默认情况下，Java采用自带的序列化方式 ，可读性很差，所以进行替换。
    @Bean
    public RedisSerializer serializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

}