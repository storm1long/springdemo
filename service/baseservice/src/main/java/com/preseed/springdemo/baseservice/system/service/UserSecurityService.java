package com.preseed.springdemo.baseservice.system.service;

import com.preseed.springdemo.baseservice.security.domain.UserSecurity;

public interface UserSecurityService {
  UserSecurity getUserSecurityByUserName(String userName);
}
