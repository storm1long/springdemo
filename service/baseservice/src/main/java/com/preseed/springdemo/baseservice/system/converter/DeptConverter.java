package com.preseed.springdemo.baseservice.system.converter;

import org.mapstruct.Mapper;

import com.preseed.springdemo.baseservice.system.model.entity.Dept;
import com.preseed.springdemo.baseservice.system.model.form.DeptForm;
import com.preseed.springdemo.baseservice.system.model.vo.DeptVO;

/**
 * 部门对象转换器
 *
 * @author haoxr
 * @since 2022/7/29
 */
@Mapper(componentModel = "spring")
public interface DeptConverter {

    DeptForm toForm(Dept entity);
    
    DeptVO toVo(Dept entity);

    Dept toEntity(DeptForm deptForm);

}