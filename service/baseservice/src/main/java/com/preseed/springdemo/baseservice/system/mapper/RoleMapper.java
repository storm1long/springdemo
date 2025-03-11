package com.preseed.springdemo.baseservice.system.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.preseed.springdemo.baseservice.system.model.entity.Role;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

  @Select("select role.id,role.name,role.code from sys_role role left join sys_user_role ur on role.id = ur.role_id where ur.user_id = #{userId}")
  List<Role> findByUserId(Long userId);

  @Select("select menu.perm from sys_menu menu left join sys_role_menu rm on menu.id = rm.menu_id where rm.role_id in (#{roleIds})")
  Set<String> findPermissionListByRoleIds(String roleIds);
    /**
     * 获取最大范围的数据权限
     *
     * @param roles
     * @return
     */
    Integer getMaximumDataScope(Set<String> roles);
}
