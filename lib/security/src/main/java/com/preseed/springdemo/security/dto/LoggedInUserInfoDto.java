package com.preseed.springdemo.security.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;


public class LoggedInUserInfoDto implements UserDetails {
  public LoggedInUserInfoDto(){
  }

  private LoggedInUserInfoDto(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }

  private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

  private Long userId;

  private String nickname;

  private String avatar;

  private Set<Long> roleIds;

  private Set<String> roleCodes;
  private Set<String> perms;

  private Long deptId;

  private LocalDateTime localDateTime = LocalDateTime.now();

  private String username;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;

  public static class Builder{
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private long userId;
    private String nickname;
    private String avatar;
    private Set<Long> roleIds;
    private Set<String> roleCodes;
    private Long deptId;
    public Builder(String username, String password, Collection<? extends GrantedAuthority> authorities){
      this.username = username;
      this.password = password;
      this.authorities = authorities;
    }
    public Long getUserId() {
      return userId;
    }
  
    public Builder setUserId(Long userId) {
      this.userId = userId;
      return this;
    }
  
    public Set<Long> getRoleIds() {
      return roleIds;
    }
  
    public Builder setRoleIds(Set<Long> roleIds) {
      this.roleIds = roleIds;
      return this;
    }
  
    public Long getDeptId() {
      return deptId;
    }
  
    public Builder setDeptId(Long deptId) {
      this.deptId = deptId;
      return this;
    }
  
    public Set<String> getRoleCodes() {
      return roleCodes;
    }
    public Builder setRoleCodes(Set<String> roleCodes) {
      this.roleCodes = roleCodes;
      return this;
    }
  
    public String getNickname() {
      return nickname;
    }
    public Builder setNickname(String nickname) {
      this.nickname = nickname;
      return this;
    }
    public String getAvatar() {
      return avatar;
    }
    public Builder setAvatar(String avatar) {
      this.avatar = avatar;
      return this;
    }
    public String getUsername() {
      return username;
    }
    public Builder setUsername(String username) {
      this.username = username;
      return this;
    }
    public String getPassword() {
      return password;
    }
    public Builder setAuthorities(Collection<? extends GrantedAuthority> authorities){
      this.authorities = authorities;
      return this;
    } 

    public LoggedInUserInfoDto build() {
      LoggedInUserInfoDto dto = new  LoggedInUserInfoDto(username,password,authorities);
      dto.setAvatar(this.avatar);
      dto.setDeptId(this.deptId);
      dto.setNickname(this.nickname);
      dto.setRoleCodes(this.roleCodes);
      dto.setUserId(this.userId);
      dto.setRoleIds(this.roleIds);

      return dto;

    }
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

  public Set<String> getRoleCodes() {
    return roleCodes;
  }
  public void setRoleCodes(Set<String> roleCodes) {
    this.roleCodes = roleCodes;
  }

  public String getNickname() {
    return nickname;
  }
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
  public String getAvatar() {
    return avatar;
  }
  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    LoggedInUserInfoDto that = (LoggedInUserInfoDto) o;

    if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
    if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
    if (avatar != null ? !avatar.equals(that.avatar) : that.avatar != null) return false;
    if (roleIds != null ? !roleIds.equals(that.roleIds) : that.roleIds != null) return false;
    if (roleCodes != null ? !roleCodes.equals(that.roleCodes) : that.roleCodes != null) return false;
    if (deptId != null ? !deptId.equals(that.deptId) : that.deptId != null) return false;
    return localDateTime != null ? localDateTime.equals(that.localDateTime) : that.localDateTime == null;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
    result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
    result = 31 * result + (roleIds != null ? roleIds.hashCode() : 0);
    result = 31 * result + (roleCodes != null ? roleCodes.hashCode() : 0);
    result = 31 * result + (deptId != null ? deptId.hashCode() : 0);
    result = 31 * result + (localDateTime != null ? localDateTime.hashCode() : 0);
    return result;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }
  public void setAuthorities(Collection<? extends GrantedAuthority> authorities){
    this.authorities = authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public void setAccountNonExpired(boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  public Set<String> getPerms() {
    return perms;
  }

  public void setPerms(Set<String> perms) {
    this.perms = perms;
  }

}
