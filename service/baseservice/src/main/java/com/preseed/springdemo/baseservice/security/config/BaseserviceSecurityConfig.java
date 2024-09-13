package com.preseed.springdemo.baseservice.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import com.preseed.springdemo.baseservice.security.SysUserDetailsService;

@EnableWebSecurity
@Configuration
public class BaseserviceSecurityConfig {

    @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    
    return http.build();
  }
  @Bean
  UserDetailsService userDetailsService(){
    return new SysUserDetailsService();
  }

}
