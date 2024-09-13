package com.preseed.springdemo.baseservice.security.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.preseed.springdemo.redis.util.RedisUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

  private RedisUtils redisUtils;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
  }

}
