package com.preseed.springdemo.baseservice.security.config;

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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.preseed.springdemo.baseservice.security.SysUserDetailsService;
import com.preseed.springdemo.security.config.TokenAuthenticationConfigurer;

@EnableWebSecurity
@Configuration
public class BaseserviceSecurityConfig {

  /**
   *  securityMatcher 配置很重要
   * http.securityMatcher("/auth/**")，如果没有配置,则所有的请求都会符合当前SecurityFilterChain ,而不会走默认的 SecurityFilterChain
   * https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html#use-new-security-matchers
   * @param http
   * @return
   * @throws Exception
   */
  @Bean
  @Order(2)
  public SecurityFilterChain baseserviceFilterChain(HttpSecurity http) throws Exception {
    http.formLogin(AbstractHttpConfigurer::disable)
    .csrf(csrf-> csrf.disable())
    .with(new TokenAuthenticationConfigurer() , token -> {})
    .authorizeHttpRequests(auth -> auth.requestMatchers("/api-baseservice/auth/login","/api-baseservice/auth/captcha").permitAll())
    ;// 自定义认证管理
    // http.authorizeHttpRequests(null)
    return http.build();
  }
  @Bean
  UserDetailsService userDetailsService() {
    return new SysUserDetailsService();
  }

  @Bean
  AuthenticationManager authenticationManager(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder) {
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

}
