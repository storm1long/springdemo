package com.preseed.springdemo.baseservice.service.impl;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.preseed.springdemo.baseservice.domain.User;
import com.preseed.springdemo.baseservice.model.dto.LoginResult;
import com.preseed.springdemo.baseservice.service.AuthService;
import com.preseed.springdemo.baseservice.service.UserService;
import com.preseed.springdemo.security.cache.UserCacheManager;

import jakarta.annotation.Resource;

@RestController("auth")
public class AuthServiceImpl implements AuthService{

  @Resource
  private AuthenticationManager authenticationManager;

  @Resource
  private UserCacheManager userCacheManager;

  @Resource
  private UserService userService;
      /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @PostMapping("login")
    @Override
    public LoginResult login(String username, String password) {
        // 创建认证令牌对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username.toLowerCase().trim(), password);
        // 执行用户认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 生成token
        String token = UUID.randomUUID().toString();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        userCacheManager.addUserCache(username, user.loginUserInfoDto(), 2000L);
        
        return new LoginResult(token, 2000L);
    }
    @Override
    public void logout() {
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }


}
