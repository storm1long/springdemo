package com.preseed.springdemo.security.config;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import jakarta.servlet.http.HttpServletRequest;

public class TokenPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    return request.getParameter("token");
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return "";
  }

}
