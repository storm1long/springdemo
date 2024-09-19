package com.preseed.springdemo.baseservice.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.preseed.springdemo.baseservice.domain.UserSecurity;
import com.preseed.springdemo.baseservice.service.UserSecurityService;

import jakarta.annotation.Resource;

public class SysUserDetailsService implements UserDetailsService {
  @Resource
  private UserSecurityService userSecurityService;

  

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserSecurity userSecurity = userSecurityService.getUserSecurityByUserName(username);
    if (userSecurity == null) {
      throw new UsernameNotFoundException(username);
    }
    return User.builder().username(username).password(userSecurity.getPassword()).build(); 
  }

}
