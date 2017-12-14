package com.lotte.redis.config;

import javax.annotation.*;

import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.*;
import org.springframework.session.data.redis.config.annotation.web.http.*;
import org.springframework.session.web.context.*;

@Configuration
@Profile("local")
@EnableRedisHttpSession
public class HttpSessionConfigLocal extends AbstractHttpSessionApplicationInitializer {

    @Resource(name = "httpSessionConnectionFactory")
    private RedisConnectionFactory connectionFactory;

    @Bean
    public RedisConnectionFactory connectionFactory() {
        return connectionFactory;
    }

}
