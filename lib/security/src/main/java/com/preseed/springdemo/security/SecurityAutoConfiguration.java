package com.preseed.springdemo.security;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.preseed.springdemo.redis.util.RedisUtils;
import com.preseed.springdemo.security.cache.UserCacheManager;
import com.preseed.springdemo.security.config.SecurityConfig;

@AutoConfiguration
@Import(SecurityConfig.class)
public class SecurityAutoConfiguration {

  @Bean
  @ConditionalOnBean(RedisUtils.class)
  public UserCacheManager userCacheManager(RedisUtils redisUtils) {
    return new UserCacheManager(redisUtils);
  }

}
