package com.preseed.springdemo.baseservice.system.service;

import com.preseed.springdemo.baseservice.system.model.dto.LoginResult;

public interface AuthService {
    LoginResult login(String username, String password);

    /**
     * 登出
     */
    void logout();
}
