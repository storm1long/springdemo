package com.preseed.springdemo.baseservice.system.model.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.preseed.springdemo.beans.base.BasePageQuery;
// 
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 角色分页查询对象
 *
 * @author Ray
 * @since 2022/6/3
 */
//@Schema(description = "角色分页查询对象")
@Getter
@Setter
public class RolePageQuery extends BasePageQuery {

    //@Schema(description="关键字(角色名称/角色编码)")
    private String keywords;

    //@Schema(description="开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;

    //@Schema(description="结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;
}
