package com.preseed.springdemo.security.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.preseed.springdemo.redis.util.RedisUtils;
import com.preseed.springdemo.security.dto.LoginUserInfoDto;

import jakarta.annotation.Resource;

// @Component
public class UserCacheManager {

    public UserCacheManager(RedisUtils redisUtils){
        this.redisUtils = redisUtils;
    }

    @Resource
    private RedisUtils redisUtils;

    private static final String CACHE_KEY = "LOGIN_USER_INFO_CACHE::";

    /**
     * 返回用户缓存
     * @param userName 用户名
     * @return JwtUserDto
     */
    public LoginUserInfoDto getUserCache(String userName) {
        if (StringUtils.isNotEmpty(userName)) {
            // 获取数据
            Object obj = redisUtils.get(CACHE_KEY + userName);
            if(obj != null){
                return (LoginUserInfoDto)obj;
            }
        }
        return null;
    }

    /**
     *  添加缓存到Redis
     * @param userName 用户名
     */
    @Async
    public void addUserCache(String userName, LoginUserInfoDto user,Long idleTime) {
        if (StringUtils.isNotEmpty(userName)) {
            redisUtils.set(CACHE_KEY + userName, user, idleTime);
        }
    }

    /**
     * 清理用户缓存信息
     * 用户信息变更时
     * @param userName 用户名
     */
    @Async
    public void cleanUserCache(String userName) {
        if (StringUtils.isNotEmpty(userName)) {
            // 清除数据
            redisUtils.del(CACHE_KEY + userName);
        }
    }
}