package com.lotte.redis.config;

import javax.annotation.*;

import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.*;
import org.springframework.session.data.redis.config.annotation.web.http.*;
import org.springframework.session.web.context.*;

@Configuration
@Profile({"dev", "test", "staging", "product"})
@EnableRedisHttpSession
public class HttpSessionConfigNonLocal extends AbstractHttpSessionApplicationInitializer {

    @Resource(name = "httpSessionClusterConnectionFactory")
    private RedisConnectionFactory clusterConnectionFactory;

    @Bean
    public RedisConnectionFactory ConnectionFactory() {
        return clusterConnectionFactory;
    }

}
