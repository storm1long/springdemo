package com.preseed.springdemo.security.config;

import java.io.IOException;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.preseed.springdemo.common.model.ResultCode;
import com.preseed.springdemo.security.config.property.CustomSecurityProperties;
import com.preseed.springdemo.security.filter.AuthFilter;
import com.preseed.springdemo.security.handler.CustomLogoutSuccessHandler;
import com.preseed.springdemo.utils.ResponseUtils;
import com.preseed.springdemo.utils.json.JSON;

import cn.hutool.core.util.ArrayUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CustomSecurityProperties.class)
public class SecurityConfig {

  // @Resource
  // private AbstractSecurityConfig customSecurityConfig;

  @Resource
  private CustomSecurityProperties customSecurityProperties;

  /**
   * 只有没有其它的 SecurityFilterChain Bean，
   * 或者 有 SecurityFilterChain 且配置了 securityMatcher ，且请求地址不匹配 securityMatcher ，
   * 当前 SecurityFilterChain 才有效
   * 
   * @param http
   * @return
   * @throws Exception
   */
  @Order(SecurityProperties.BASIC_AUTH_ORDER)
  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, AuthFilter authFilter,
      LogoutSuccessHandler customLogoutSuccessHandler) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)// 禁用 CSRF
        .cors(AbstractHttpConfigurer::disable)// 禁用跨域
        .formLogin(AbstractHttpConfigurer::disable)// 禁用表单登录
        .httpBasic(AbstractHttpConfigurer::disable) // 禁用 HTTP Basic 认证，避免弹窗式登录
        .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// 禁用 session
    ;
    http
    // 异常处理
        .exceptionHandling(exception -> exception.authenticationEntryPoint((req, resp,
            ex) -> {
          if (ex instanceof BadCredentialsException) {
            // 用户名或密码错误
            ResponseUtils.writeErrMsg(resp, ResultCode.USER_PASSWORD_ERROR);
          } else if (ex instanceof InsufficientAuthenticationException) {
            // 请求头缺失Authorization、Token格式错误、Token过期、签名验证失败
            ResponseUtils.writeErrMsg(resp, ResultCode.ACCESS_TOKEN_INVALID);
          } else {
            // 其他未明确处理的认证异常（如账户被锁定、账户禁用等）
            ResponseUtils.writeErrMsg(resp, ResultCode.USER_LOGIN_EXCEPTION, ex.getMessage());
          }
        })
        // 无权限访问异常处理器
        .accessDeniedHandler((req, resp, ex) -> ResponseUtils.writeErrMsg(resp, ResultCode.ACCESS_UNAUTHORIZED)) )
        .addFilterBefore(authFilter, LogoutFilter.class)
        .logout(logout -> logout.logoutUrl("/logout").logoutSuccessHandler(customLogoutSuccessHandler));

    http.authorizeHttpRequests(authorizeHttpRequests -> {
      // 忽略认证的 URI 地址
      String[] whiteListPaths = customSecurityProperties.getWhiteListPaths();
      if (ArrayUtil.isNotEmpty(whiteListPaths)) {
        authorizeHttpRequests.requestMatchers(whiteListPaths).permitAll();
      }
      authorizeHttpRequests.anyRequest().authenticated();
    });
    // if (customSecurityConfig != null) {
    // customSecurityConfig.customConfig(http);
    // }
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthFilter authFilter() {
    return new AuthFilter();
  }

  @Bean
  private LogoutSuccessHandler customLogoutSuccessHandler() {
    CustomLogoutSuccessHandler customLogoutSuccessHandler = new CustomLogoutSuccessHandler();
    // customLogoutSuccessHandler.setDefaultTargetUrl("/logout_success");
    // customLogoutSuccessHandler.setEmailService(emailService);
    // customLogoutSuccessHandler.setSmsService(smsService);
    // customLogoutSuccessHandler.setWeChatService(weChatService);
    return customLogoutSuccessHandler;
  }
}
