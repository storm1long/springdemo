package com.preseed.springdemo.baseservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaResult {
    //"验证码ID")
    private String captchaKey;
    //"验证码图片Base64字符串")
    private String captchaBase64;

}
