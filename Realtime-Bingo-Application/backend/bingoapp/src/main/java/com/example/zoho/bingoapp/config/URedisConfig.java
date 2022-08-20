package com.example.zoho.bingoapp.config;

import com.example.zoho.bingoapp.repository.RedisDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackageClasses = RedisDao.class, redisTemplateRef = "RedisUserRedisTemplate")
public class URedisConfig {
    @Bean(name = "RedisUserFactory")
    public RedisConnectionFactory connectionFactory() {

        return new JedisConnectionFactory();
    }

    @Bean(name = "RedisUserRedisTemplate")
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        return template;
    }
}
