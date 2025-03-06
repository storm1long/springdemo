package com.preseed.springdemo.baseservice.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.preseed.springdemo.baseservice.model.entity.DictData;
import com.preseed.springdemo.baseservice.model.form.DictDataForm;
import com.preseed.springdemo.baseservice.model.vo.DictPageVO;
import com.preseed.springdemo.common.model.Option;
import com.preseed.springdemo.baseservice.model.form.DictForm;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 字典项 对象转换器
 *
 * @author Ray
 * @since 2022/6/8
 */
@Mapper(componentModel = "spring")
public interface DictDataConverter {

    Page<DictPageVO> toPageVo(Page<DictData> page);

    DictDataForm toForm(DictData entity);

    DictData toEntity(DictDataForm formFata);

    Option<Long> toOption(DictData dictData);
    List<Option<Long>> toOption(List<DictData> dictData);
}
