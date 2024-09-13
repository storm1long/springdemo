package com.preseed.springdemo.baseservice.controller;

import java.awt.Font;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.preseed.springdemo.baseservice.common.constant.SecurityConstants;
import com.preseed.springdemo.baseservice.config.property.CaptchaProperties;
import com.preseed.springdemo.baseservice.model.dto.CaptchaResult;
import com.preseed.springdemo.baseservice.model.dto.LoginResult;
import com.preseed.springdemo.baseservice.service.AuthService;
import com.preseed.springdemo.redis.util.RedisUtils;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.IdUtil;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Resource
  private CaptchaProperties captchaProperties;

  @Resource
  private CodeGenerator codeGenerator;

  @Resource
  private Font captchaFont;

  @Resource
  private AuthService authService;

  private RedisUtils redisUtils;

  @PostMapping("/login")
  public ResponseEntity<LoginResult> login(
      @RequestParam String username,
      @RequestParam String password) {
    LoginResult loginResult = authService.login(username, password);
    return ResponseEntity.ok(loginResult);
  }

  public ResponseEntity<CaptchaResult> captcha() {
    int width = captchaProperties.getWidth();
    int height = captchaProperties.getHeight();
    int interfereCount = captchaProperties.getInterfereCount();
    int codeLength = captchaProperties.getCode().getLength();
    AbstractCaptcha captcha = CaptchaUtil.createCircleCaptcha(width, height, codeLength, interfereCount);
    captcha.setGenerator(codeGenerator);
    captcha.setTextAlpha(captchaProperties.getTextAlpha());
    captcha.setFont(captchaFont);

    String captchaCode = captcha.getCode();
    String imageBase64Data = captcha.getImageBase64Data();

    // 验证码文本缓存至Redis，用于登录校验
    String captchaKey = IdUtil.fastSimpleUUID();
    redisUtils.set(SecurityConstants.CAPTCHA_CODE_PREFIX + captchaKey, captchaCode,
        captchaProperties.getExpireSeconds());

    return ResponseEntity.ok(CaptchaResult.builder()
        .captchaKey(captchaKey)
        .captchaBase64(imageBase64Data)
        .build());

  }

}
