package com.preseed.springdemo.baseservice.model.query;

// 
import lombok.Data;

/**
 * 菜单查询对象
 *
 * @author haoxr
 * @since 2022/10/28
 */
// @Schema(description ="菜单查询对象")
@Data
public class MenuQuery {

    // @Schema(description="关键字(菜单名称)")
    private String keywords;

    // @Schema(description="状态(1->显示；0->隐藏)")
    private Integer status;

}
