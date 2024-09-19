package com.preseed.springdemo.security.config;

import java.io.IOException;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import com.preseed.springdemo.utils.json.JSON;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

  /**
   * 只有没有其它的 SecurityFilterChain Bean，
   * 或者 有 SecurityFilterChain 且配置了 securityMatcher ，且请求地址不匹配 securityMatcher ，
   * 当前 SecurityFilterChain 才有效
   * @param http
   * @return
   * @throws Exception
   */
  @Order(SecurityProperties.BASIC_AUTH_ORDER)
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(requests -> requests.anyRequest().authenticated());
    http.csrf(csrf -> csrf.disable());
    http.with(new TokenAuthenticationConfigurer() , token -> {}).exceptionHandling(exception -> exception.authenticationEntryPoint(new AuthenticationEntryPoint() {

          @Override
          public void commence(HttpServletRequest request, HttpServletResponse response,
              AuthenticationException authException) throws IOException, ServletException {
                response.setContentType("application/json; charset=UTF-8");
                
                response.getWriter().println(JSON.toJsonString(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)));
          }
          
        }));
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public AuthenticationEntryPoint authenticationEntryPoint(){
    return new AuthenticationEntryPoint() {

      @Override
      public void commence(HttpServletRequest request, HttpServletResponse response,
          AuthenticationException authException) throws IOException, ServletException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'commence'");
      }
      
    };
  }

}
