package com.preseed.springdemo.security.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

public class LoginUserInfoDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private String username;
  private Long userId;

  private Set<Long> roleIds;

  private Long deptId;

  private LocalDateTime localDateTime = LocalDateTime.now();

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Set<Long> getRoleIds() {
    return roleIds;
  }

  public void setRoleIds(Set<Long> roleIds) {
    this.roleIds = roleIds;
  }

  public Long getDeptId() {
    return deptId;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }

  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }

  public void setLocalDateTime(LocalDateTime localDateTime) {
    this.localDateTime = localDateTime;
  }

}
