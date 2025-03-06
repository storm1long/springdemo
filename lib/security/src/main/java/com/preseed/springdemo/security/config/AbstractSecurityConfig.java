package com.preseed.springdemo.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public abstract class AbstractSecurityConfig {
  public abstract void customConfig(HttpSecurity http) throws Exception ;
}
