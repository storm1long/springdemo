package com.preseed.springdemo.baseservice.converter;

import com.preseed.springdemo.baseservice.model.entity.Dept;
import com.preseed.springdemo.baseservice.model.vo.DeptVO;
import com.preseed.springdemo.baseservice.model.form.DeptForm;
import org.mapstruct.Mapper;

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