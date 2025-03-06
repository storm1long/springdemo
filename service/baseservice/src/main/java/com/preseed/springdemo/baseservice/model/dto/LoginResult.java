package com.preseed.springdemo.baseservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResult {
    public LoginResult(){
    }
    public LoginResult(String accessToken, Long expires){
        this.accessToken = accessToken;
        this.expires = expires;
        this.isAuthenticated = true;
    }
    private boolean isAuthenticated = false;
    //"访问token"
    private String accessToken;

    // 过期时间(单位：毫秒)
    private Long expires;

}
