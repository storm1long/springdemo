package com.preseed.springdemo.baseservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.preseed.springdemo.baseservice.domain.User;
import com.preseed.springdemo.baseservice.mapper.UserMapper;
import com.preseed.springdemo.baseservice.service.UserService;

import jakarta.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

  @Resource
  private UserMapper userMapper;
  @Override
  public User getUserAuthInfo(String username) {
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.eq("username", username);

    List<User> users = userMapper.selectObjs(wrapper);
    if (users.isEmpty()){
      return null;
    }
    if(users.size() > 1){
      throw new RuntimeException("More than one user found for username: " + username);
    }
    return users.get(0);
  }

}
