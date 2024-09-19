package com.preseed.springdemo.baseservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResult {
    public LoginResult(){
    }
    public LoginResult(String token, Long expires){
        this.token = token;
        this.expires = expires;
        this.isAuthenticated = true;
    }
    private boolean isAuthenticated = false;
    //"访问token"
    private String token;

    // 过期时间(单位：毫秒)
    private Long expires;

}
