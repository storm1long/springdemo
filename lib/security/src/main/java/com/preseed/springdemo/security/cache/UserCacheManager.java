package com.preseed.springdemo.security.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;

import com.preseed.springdemo.redis.util.RedisUtils;
import com.preseed.springdemo.security.dto.LoggedInUserInfoDto;

import jakarta.annotation.Resource;

public class UserCacheManager {

    public UserCacheManager(RedisUtils redisUtils){
        this.redisUtils = redisUtils;
    }

    @Resource
    private RedisUtils redisUtils;

    private static final String CACHE_KEY = "LOGIN_USER_INFO_CACHE::";

    /**
     * 返回用户缓存
     * @param token 用户名
     */
    public LoggedInUserInfoDto getUserCache(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 获取数据
            Object obj = redisUtils.get(CACHE_KEY + token);
            if(obj != null){
                return (LoggedInUserInfoDto)obj;
            }
        }
        return null;
    }

    /**
     *  添加缓存到Redis
     * @param token 用户名
     */
    @Async
    public void addUserCache(String token, LoggedInUserInfoDto user,Long idleTime) {
        if (StringUtils.isNotEmpty(token)) {
            redisUtils.set(CACHE_KEY + token, user, idleTime);
        }
    }

    /**
     * 清理用户缓存信息
     * 用户信息变更时
     * @param token 用户名
     */
    @Async
    public void cleanUserCache(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 清除数据
            redisUtils.del(CACHE_KEY + token);
        }
    }
}