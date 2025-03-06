package com.preseed.springdemo.baseservice.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.preseed.springdemo.baseservice.model.entity.User;
import com.preseed.springdemo.baseservice.model.vo.UserInfoVO;
import com.preseed.springdemo.baseservice.model.vo.UserPageVO;
import com.preseed.springdemo.baseservice.model.vo.UserProfileVO;
import com.preseed.springdemo.common.model.Option;
import com.preseed.springdemo.baseservice.model.bo.UserBO;
import com.preseed.springdemo.baseservice.model.dto.UserImportDTO;
import com.preseed.springdemo.baseservice.model.form.UserForm;
import com.preseed.springdemo.baseservice.model.form.UserProfileForm;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 用户对象转换器
 *
 * @author Ray.Hao
 * @since 2022/6/8
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    UserPageVO toPageVo(UserBO bo);

    Page<UserPageVO> toPageVo(Page<UserBO> bo);

    UserForm toForm(User entity);

    @InheritInverseConfiguration(name = "toForm")
    User toEntity(UserForm entity);

    @Mappings({
            @Mapping(target = "userId", source = "id")
    })
    UserInfoVO toUserInfoVo(User entity);

    User toEntity(UserImportDTO vo);


    UserProfileVO toProfileVO(UserBO bo);

    User toEntity(UserProfileForm formData);

    @Mappings({
            @Mapping(target = "label", source = "nickname"),
            @Mapping(target = "value", source = "id")
    })
    Option<String> toOption(User entity);

    List<Option<String>> toOptions(List<User> list);
}
