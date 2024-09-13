package com.preseed.springdemo.baseservice.service.impl;

import java.util.List;

import com.preseed.springdemo.baseservice.domain.UserSecurity;
import com.preseed.springdemo.baseservice.mapper.UserSecurityMapper;
import com.preseed.springdemo.baseservice.service.UserSecurityService;

import jakarta.annotation.Resource;

public class UserSecurityServiceImpl implements UserSecurityService {

  @Resource
  private UserSecurityMapper userSecurityMapper;
  @Override
  public UserSecurity getUserSecurityByUserName(String username) {
    List<UserSecurity> userSecurityList = userSecurityMapper.selectOnSecurityByName(username);
    if(userSecurityList.isEmpty()) {
      return null;
    }
    if(userSecurityList.size() > 1){
      throw new RuntimeException("More than one user found for username: " + username);
    }
    return userSecurityList.get(0);
  }

}
