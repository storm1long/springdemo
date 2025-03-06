package com.preseed.springdemo.baseservice.security.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.preseed.springdemo.baseservice.security.SysUserDetailsService;
import com.preseed.springdemo.security.cache.UserCacheManager;
import com.preseed.springdemo.security.config.AbstractSecurityConfig;
import com.preseed.springdemo.security.config.TokenAuthenticationConfigurer;
import com.preseed.springdemo.security.filter.AuthFilter;
import com.preseed.springdemo.security.handler.CustomLogoutSuccessHandler;

import jakarta.annotation.Resource;

// @EnableWebSecurity
@Configuration
// @Component
public class BaseserviceSecurityConfig extends AbstractSecurityConfig {

  @Resource
  private UserCacheManager userCacheManager;

  /**
   * @order 很重要，需要小于 defaultSecurityFilterChain 中的 order 值 BASIC_AUTH_ORDER（2147483647 -5 ） 
   * securityMatcher 配置很重要
   * http.securityMatcher("/auth/**")，如果没有配置,则所有的请求都会符合当前SecurityFilterChain
   * ,而不会走默认的 SecurityFilterChain
   * https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html#use-new-security-matchers
   * 
   * @param http
   * @return
   * @throws Exception
   */
  @Order(1)
  // @Bean
  public SecurityFilterChain baseServiceSecurityConfig(HttpSecurity http) throws Exception {
    // http.securityMatcher("/auth/**")
    http.securityMatcher("/auth/**")
        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
        .requestMatchers("/auth/login", "/auth/captcha").permitAll()
        .anyRequest().authenticated());
    return http.build();

  }

  public void customConfig(HttpSecurity http) throws Exception {
    // http
    //     .authorizeHttpRequests(request -> {
    //       request
    //           .requestMatchers("/auth/login", "/auth/captcha").permitAll()
    //           .anyRequest().authenticated();
    //     })
    // ;
  }

  @Bean
  UserDetailsService userDetailsService() {
    return new SysUserDetailsService();
  }

  @Bean
  AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return new ProviderManager(provider);

  }

  // @Bean
  AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    return new AuthenticationProvider() {

      @Override
      public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String password = authentication.getCredentials().toString();
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
          return new UsernamePasswordAuthenticationToken(userDetailsService, passwordEncoder);

        } else {
          throw new BadCredentialsException("密码错误");
        }
      }

      @Override
      public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'supports'");
      }

    };
  }

  // @Bean
  PreAuthenticatedAuthenticationProvider tokenProvider() {
    PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
    provider.setPreAuthenticatedUserDetailsService(
        new AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>() {
          @Override
          public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token)
              throws UsernameNotFoundException {
            System.out.println(token.getPrincipal());
            System.out.println(token.getPrincipal());
            return null;
          }
        });
    return provider;
  }

}
