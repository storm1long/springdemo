package com.preseed.springdemo.baseservice.system.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.preseed.springdemo.baseservice.system.model.entity.Menu;
import com.preseed.springdemo.baseservice.system.model.form.MenuForm;
import com.preseed.springdemo.baseservice.system.model.vo.MenuVO;

/**
 * 菜单对象转换器
 *
 * @author Ray Hao
 * @since 2024/5/26
 */
@Mapper(componentModel = "spring")
public interface MenuConverter {

    MenuVO toVo(Menu entity);

    @Mapping(target = "params", ignore = true)
    MenuForm toForm(Menu entity);

    @Mapping(target = "params", ignore = true)
    Menu toEntity(MenuForm menuForm);

}