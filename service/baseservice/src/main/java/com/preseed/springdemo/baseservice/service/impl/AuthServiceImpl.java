package com.preseed.springdemo.baseservice.service.impl;

import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.preseed.springdemo.baseservice.model.dto.LoginResult;
import com.preseed.springdemo.baseservice.model.entity.Role;
import com.preseed.springdemo.baseservice.service.AuthService;
import com.preseed.springdemo.baseservice.service.RoleService;
import com.preseed.springdemo.baseservice.service.UserService;
import com.preseed.springdemo.security.SecurityUtils;
import com.preseed.springdemo.security.cache.UserCacheManager;
import com.preseed.springdemo.security.constant.SecurityConstants;
import com.preseed.springdemo.security.dto.LoggedInUserInfoDto;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import jakarta.annotation.Resource;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserCacheManager userCacheManager;

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @Override
    public LoginResult login(String username, String password) {
        // 创建认证令牌对象
        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(
                username, password);
        // 执行用户认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 生成token
        String token = IdUtil.fastUUID();
        if (authentication.isAuthenticated()) {
            LoggedInUserInfoDto loggedInUserInfoDto = (LoggedInUserInfoDto) authentication.getPrincipal();
            List<Role> roles = roleService.getRolesByUserId(loggedInUserInfoDto.getUserId());
            loggedInUserInfoDto
                    .setRoleIds(roles.stream().map(Role::getId).collect(java.util.stream.Collectors.toSet()));
            loggedInUserInfoDto
                    .setRoleCodes(roles.stream().map(Role::getCode).collect(java.util.stream.Collectors.toSet()));
            loggedInUserInfoDto.setPerms(roleService.getPermissionByRoleIds(loggedInUserInfoDto.getRoleIds()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            userCacheManager.addUserCache(token, loggedInUserInfoDto, 2000L);
            return new LoginResult(token, 2000L);
        } else {
            return new LoginResult();
        }

    }

    @Override
    public void logout() {
        String token = SecurityUtils.getTokenFromRequest();
        if (CharSequenceUtil.isNotBlank(token) && token.startsWith(SecurityConstants.JWT_TOKEN_PREFIX)) {
            token = token.substring(SecurityConstants.JWT_TOKEN_PREFIX.length());
            // 将JWT令牌加入黑名单
            userCacheManager.cleanUserCache(token);
            // 清除Security上下文
            SecurityContextHolder.clearContext();
        }
    }

}
