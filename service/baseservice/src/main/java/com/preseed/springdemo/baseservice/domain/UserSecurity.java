package com.preseed.springdemo.baseservice.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_user")
public class UserSecurity implements Serializable{
    @TableId(value="id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String username;

    private LocalDateTime updateTime;
}
