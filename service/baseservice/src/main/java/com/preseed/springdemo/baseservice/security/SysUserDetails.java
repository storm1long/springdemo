package com.preseed.springdemo.baseservice.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.preseed.springdemo.baseservice.domain.UserSecurity;

public class SysUserDetails implements UserDetails{
  private UserSecurity user;

  public SysUserDetails(UserSecurity user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

}
