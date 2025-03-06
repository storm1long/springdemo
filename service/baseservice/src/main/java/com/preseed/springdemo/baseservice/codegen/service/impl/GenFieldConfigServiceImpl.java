package com.preseed.springdemo.baseservice.codegen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.preseed.springdemo.baseservice.codegen.mapper.GenFieldConfigMapper;
import com.preseed.springdemo.baseservice.codegen.model.entity.GenFieldConfig;
import com.preseed.springdemo.baseservice.codegen.service.GenFieldConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 代码生成字段配置服务实现类
 *
 * @author Ray
 * @since 2.10.0
 */
@Service
@RequiredArgsConstructor
public class GenFieldConfigServiceImpl extends ServiceImpl<GenFieldConfigMapper, GenFieldConfig> implements GenFieldConfigService {


}
