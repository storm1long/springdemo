package com.preseed.springdemo.baseservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.preseed.springdemo.baseservice.config.property.CaptchaProperties;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import jakarta.annotation.Resource;
import java.awt.Font;

@Configuration
public class CaptchaConfig {
  @Resource
  private CaptchaProperties captchaProperties;

  /**
   * 文字生成器
   */
  @Bean
  public CodeGenerator codeGenerator() {
    String codeType = captchaProperties.getCode().getType();
    int codeLength = captchaProperties.getCode().getLength();
    if ("math".equalsIgnoreCase(codeType)) {
      return new MathGenerator(codeLength);
    } else if ("random".equalsIgnoreCase(codeType)) {
      return new RandomGenerator(codeLength);
    } else {
      throw new IllegalArgumentException("Invalid captcha codegen type: " + codeType);
    }
  }

  /**
   * 验证码字体
   */
  @Bean
  public Font captchaFont() {
    String fontName = captchaProperties.getFont().getName();
    int fontSize = captchaProperties.getFont().getSize();
    int fontWight = captchaProperties.getFont().getWeight();
    return new Font(fontName, fontWight, fontSize);
  }

}
