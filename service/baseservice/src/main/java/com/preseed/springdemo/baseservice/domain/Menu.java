package com.preseed.springdemo.baseservice.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.preseed.springdemo.beans.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_menu")
public class Menu extends BaseEntity implements Serializable {

    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单对应的角色列表
     */
    @TableField(exist = false)
    // @JSONField(serialize = false)
    private Set<Role> roles;

    @TableField(exist = false)
    private List<Menu> children;

    /**
     * 菜单标题
     */
    private String title;

    /**
     * 组件名
     */
    @TableField(value = "name")
    private String componentName;

    /**
     * 排序
     */
    private Integer menuSort = 999;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 菜单类型
     * 目录，菜单，按钮
     */
    private Integer type;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单图标
     */
    private String icon;

    private Boolean cache;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 父级菜单
     */
    private Long pid;

    private Integer subCount = 0;

    /**
     * 是否是外链菜单
     */
    private Boolean iFrame;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Boolean getHasChildren() {
        return subCount > 0;
    }

    public Boolean getLeaf() {
        return subCount <= 0;
    }

    public String getLabel() {
        return title;
    }
}