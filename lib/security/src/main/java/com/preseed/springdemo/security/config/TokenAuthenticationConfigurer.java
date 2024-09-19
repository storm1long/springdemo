package com.preseed.springdemo.security.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class TokenAuthenticationConfigurer extends AbstractHttpConfigurer<TokenAuthenticationConfigurer, HttpSecurity> {
  
  @Override
  public void init(HttpSecurity http) throws Exception{

    http.csrf(csrf -> csrf.disable()).formLogin(form -> form.disable());
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    final AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
    AbstractPreAuthenticatedProcessingFilter tokenFilter = new TokenPreAuthenticatedProcessingFilter();
    tokenFilter.setAuthenticationManager(authenticationManager);
    http.addFilter(tokenFilter);
    super.configure(http);
  }

  public TokenAuthenticationConfigurer(){
    super();
  }


}
