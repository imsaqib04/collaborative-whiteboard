package com.example.whiteboard.config;

import com.example.whiteboard.model.DrawMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer; // <-- Bas ye 1 import chahiye Serializer ke liye

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, DrawMessage> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, DrawMessage> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 🚀 Naya Spring 4.0 Tarika: Seedha interface ke static methods use karo
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());

        return template;
    }
}