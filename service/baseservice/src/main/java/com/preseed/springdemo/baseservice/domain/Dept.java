package com.preseed.springdemo.baseservice.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.preseed.springdemo.beans.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@TableName("sys_dept")
public class Dept extends BaseEntity implements Serializable {

    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long id;

    /**
     * 部门对应角色列表
     */
    @TableField(exist = false)
    private Set<Role> roles;

    @TableField(exist = false)
    private List<Dept> children;

    /**
     * 排序字段
     */
    private Integer deptSort;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 状态，是否启用
     */
    private Boolean enabled;

    /**
     * 父级部门
     */
    private Long pid;

    private Integer subCount = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dept dept = (Dept) o;
        return Objects.equals(id, dept.id) &&
                Objects.equals(name, dept.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Boolean getHasChildren() {
        return subCount > 0;
    }

    public Boolean getLeaf() {
        return subCount <= 0;
    }

    public String getLabel() {
        return name;
    }
}
