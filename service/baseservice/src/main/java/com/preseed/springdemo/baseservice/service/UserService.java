package com.preseed.springdemo.baseservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.preseed.springdemo.baseservice.domain.User;

public interface UserService extends IService<User>{
  User getUserAuthInfo(String username);
}
