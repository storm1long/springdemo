package com.preseed.springdemo.security.filter;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.preseed.springdemo.security.SecurityUtils;
import com.preseed.springdemo.security.cache.UserCacheManager;
import com.preseed.springdemo.security.dto.LoggedInUserInfoDto;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthFilter extends OncePerRequestFilter {

  // 令牌自定义标识
  @Value("${token.header}")
  private String header;

  @Resource
  private UserCacheManager userCacheManager;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull  HttpServletResponse response,@NonNull  FilterChain filterChain)
      throws ServletException, IOException {
    Optional.ofNullable(getToken(request)).map(userCacheManager::getUserCache).ifPresent(SecurityUtils::setUser);
    filterChain.doFilter(request, response);
  }

  /**
   * 获取请求token
   *
   * @param request
   * @return token
   */
  private String getToken(HttpServletRequest request) {
    return request.getHeader(header);
  }

}
