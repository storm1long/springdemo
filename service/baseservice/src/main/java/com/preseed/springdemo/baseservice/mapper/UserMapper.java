package com.preseed.springdemo.baseservice.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.preseed.springdemo.baseservice.domain.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
