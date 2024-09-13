package com.preseed.springdemo.baseservice.model.dto;

import lombok.Data;

@Data
public class LoginResult {
    public LoginResult(String token, Long expires){
        this.token = token;
        this.expires = expires;
    }
    //"访问token"
    private String token;

    // 过期时间(单位：毫秒)
    private Long expires;

}
