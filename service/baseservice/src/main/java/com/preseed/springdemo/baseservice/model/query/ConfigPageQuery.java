package com.preseed.springdemo.baseservice.model.query;

import com.preseed.springdemo.beans.base.BasePageQuery;
// 
import lombok.Getter;
import lombok.Setter;

/**
 * 系统配置查询对象
 *
 * @author Theo
 * @since 2024-7-29 11:38:00
 */
@Getter
@Setter
//@Schema(description = "系统配置分页查询")
public class ConfigPageQuery extends BasePageQuery {

    //@Schema(description="关键字(配置项名称/配置项值)")
    private String keywords;
}
