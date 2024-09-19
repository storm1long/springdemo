package com.preseed.springdemo.baseservice.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginVo {

  private String username;

  private String password;

  private String captchaKey;

  private String captchaCode;

}
