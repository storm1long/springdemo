package com.preseed.springdemo.baseservice.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;

import lombok.Getter;
import lombok.Setter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.preseed.springdemo.beans.model.BaseEntity;
import com.preseed.springdemo.security.dto.LoginUserInfoDto;

import java.util.Date;

@Getter
@Setter
@TableName("sys_user")
public class User extends BaseEntity implements Serializable {

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色
     */
    @TableField(exist = false)
    private Set<Role> roles;

    /**
     * 岗位
     */
    @TableField(exist = false)
    private Set<Job> jobs;

    /**
     * 部门 id
     */
    @TableField(value = "dept_id")
    private Long deptId;

    /**
     * 部门对象
     */
    @TableField(exist = false)
    private Dept dept;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 性别
     */
    private String gender;

    /**
     * 头像名
     */
    private String avatarName;

    /**
     * 头像保存路径
     */
    private String avatarPath;

    /**
     * 状态 是否启用
     */
    private Boolean enabled;

    /**
     * 是否为管理员账号
     */
    private Boolean isAdmin = false;

    /**
     * 密码最后 重置时间
     */
    private Date pwdResetTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    public LoginUserInfoDto loginUserInfoDto() {
        LoginUserInfoDto loginUserInfoDto = new LoginUserInfoDto();
        loginUserInfoDto.setUsername(this.username);
        loginUserInfoDto.setUserId(this.id);
        return loginUserInfoDto;
    }
}