package com.preseed.springdemo.redis.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

// @ComponentScan("com.pttl.tlmall.lib.redis.util")
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class SdRedisAutoConfiguration {

    @Bean
    public RedisUtils redisUtil(@Qualifier("redisTemplate")RedisTemplate<Object, Object> redisTemplate){
        return new RedisUtils(redisTemplate);
    }

    /**
     * redisTemplate 序列化使用的jdkSerializeable, 存储二进制字节码, 所以自定义序列化类
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    // @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setKeySerializer(stringRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        return new Jackson2JsonRedisSerializer<>(objectMapper,Object.class);
    }
    @SuppressWarnings("null")
    @Bean
    @ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
    @ConditionalOnMissingBean(name = "cacheManager")
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 配置序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
        .serializeValuesWith(RedisSerializationContext.SerializationPair.
        // GenericJackson2JsonRedisSerializer 会导致缓存失效,格式不对("@class": "com.pttl.tlmall.organization.domain.PttlBranch",)
        // fromSerializer(new GenericJackson2JsonRedisSerializer()));
        fromSerializer(jackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config).build();

    }
}