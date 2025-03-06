package com.preseed.springdemo.baseservice.converter;

import com.preseed.springdemo.baseservice.model.entity.Menu;
import com.preseed.springdemo.baseservice.model.vo.MenuVO;
import com.preseed.springdemo.baseservice.model.form.MenuForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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