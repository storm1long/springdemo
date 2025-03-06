package com.preseed.springdemo.security;

import com.preseed.springdemo.constant.SystemConstants;
import com.preseed.springdemo.security.dto.LoggedInUserInfoDto;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Security 工具类
 *
 */
public class SecurityUtils {
    private SecurityUtils() {}

    /**
     * 获取当前登录人信息
     *
     * @return Optional<SysUserDetails>
     */
    public static Optional<LoggedInUserInfoDto> getUserOptional() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
                return Optional.of((LoggedInUserInfoDto) principal);
        }
        return Optional.empty();
    }

    public static LoggedInUserInfoDto getUser() {
        return getUserOptional().orElse(null);
    }

    public static void setUser(LoggedInUserInfoDto loginUser) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }


    /**
     * 获取用户ID
     *
     * @return Long
     */
    public static Long getUserId() {
        return getUserOptional().map(LoggedInUserInfoDto::getUserId).orElse(null);
    }


    /**
     * 获取用户账号
     *
     * @return String 用户账号
     */
    public static String getUsername() {
        return getUserOptional().map(LoggedInUserInfoDto::getUsername).orElse(null);
    }


    /**
     * 获取部门ID
     *
     * @return Long
     */
    public static Long getDeptId() {
        return getUserOptional().map(LoggedInUserInfoDto::getDeptId).orElse(null);
    }

    /**
     * 获取数据权限范围
     *
     * @return Integer
     */
    // public static Integer getDataScope() {
    //     return getUser().map(LoginUserInfoDto::getDataScope).orElse(null);
    // }


    /**
     * 获取用户角色集合
     *
     * @return 角色集合
     */
    public static Set<String> getRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            LoggedInUserInfoDto principal = (LoggedInUserInfoDto)authentication.getPrincipal();
            return principal.getRoleCodes();
        }
        return Collections.emptySet();
    }
    /**
     * 是否超级管理员
     * <p>
     * 超级管理员忽视任何权限判断
     */
    public static boolean isRoot() {
        Set<String> roles = getRoles();
        return roles.contains(SystemConstants.ROOT_ROLE_CODE);
    }

        /**
     * 获取请求中的 Token
     *
     * @return Token 字符串
     */
    public static String getTokenFromRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }


}