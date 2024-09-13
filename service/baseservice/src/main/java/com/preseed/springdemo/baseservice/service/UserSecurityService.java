package com.preseed.springdemo.baseservice.service;

import com.preseed.springdemo.baseservice.domain.UserSecurity;

public interface UserSecurityService {
  UserSecurity getUserSecurityByUserName(String userName);
}
