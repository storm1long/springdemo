package com.preseed.springdemo.security.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.preseed.springdemo.security.SecurityUtils;
import com.preseed.springdemo.security.cache.UserCacheManager;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{

  @Resource
  UserCacheManager userCacheManager;
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
        // super.onLogoutSuccess(request, response, authentication);
        String token = request.getHeader("");
        SecurityUtils.getUserOptional().ifPresent(loggedUser ->  userCacheManager.cleanUserCache(token));
  }

}
