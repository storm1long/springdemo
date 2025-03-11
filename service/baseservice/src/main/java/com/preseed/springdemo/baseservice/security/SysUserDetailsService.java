package com.preseed.springdemo.baseservice.security;

import java.util.Set;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.preseed.springdemo.baseservice.security.domain.UserSecurity;
import com.preseed.springdemo.baseservice.system.service.UserSecurityService;
import com.preseed.springdemo.baseservice.system.service.UserService;
import com.preseed.springdemo.security.dto.LoggedInUserInfoDto;

import jakarta.annotation.Resource;

public class SysUserDetailsService implements UserDetailsService {
  @Resource
  private UserSecurityService userSecurityService;

  @Resource
  private UserService userService;

  

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserSecurity userSecurity = userSecurityService.getUserSecurityByUserName(username);
    if (userSecurity == null) {
      throw new UsernameNotFoundException(username);
    }
    com.preseed.springdemo.baseservice.system.model.entity.User user = userService.getById(userSecurity.getUserId());
    

    // Set<SimpleGrantedAuthority> authentications = roles.stream().map(r -> new SimpleGrantedAuthority(r.getCode())).collect(Collectors.toSet());
    return new LoggedInUserInfoDto.Builder(userSecurity.getUsername(), userSecurity.getPassword(), Set.of())
    .setUserId(user.getId())
    .setAvatar(user.getAvatar()).setDeptId(user.getDeptId()).setNickname(user.getNickname())
    // .setRoleIds(roles.stream().map(Role::getId).collect(Collectors.toSet()))
    .build();
  }

}
