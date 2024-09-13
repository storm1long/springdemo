package com.preseed.springdemo.baseservice.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.preseed.springdemo.beans.enums.DataScopeEnum;
import com.preseed.springdemo.beans.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_role")
public class Role extends BaseEntity implements Serializable {

    /**
     * 角色 id
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long id;

    /***
     * 用户集合
     */
    @TableField(exist = false)
    private Set<User> users;

    /**
     * 对应菜单集合
     */
    @TableField(exist = false)
    private Set<Menu> menus;

    /**
     * 对应部门集合
     */
    @TableField(exist = false)
    private Set<Dept> depts;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 数据权限
     * 全部
     * 所在部门
     * 所在部门和所有子部门
     * 自定义
     */
    private String dataScope = DataScopeEnum.ALL.getValue();

    // 级别，数值越小，级别越大
    private Integer level = 0;

    /**
     * 描述
     */
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
