package com.preseed.springdemo.baseservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.preseed.springdemo.baseservice.domain.UserSecurity;

@Mapper
public interface UserSecurityMapper extends BaseMapper<UserSecurity> {

  @Select("SELECT su.username,sus.id,sus.user_id userId,sus.password from sys_user su left join sys_user_security sus on su.user_id = sus.user_id where su.username = #{userName}")
  List<UserSecurity> selectOnSecurityByName(String userName);

}
