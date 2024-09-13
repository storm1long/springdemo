package com.preseed.springdemo.baseservice.service;

import com.preseed.springdemo.baseservice.model.dto.LoginResult;

public interface AuthService {
    LoginResult login(String username, String password);

    /**
     * 登出
     */
    void logout();
}
