package com.preseed.springdemo.baseservice.model.query;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

import com.preseed.springdemo.beans.base.BasePageQuery;

/**
 * 日志分页查询对象
 *
 * @author Ray
 * @since 2.10.0
 */
//@Schema(description = "日志分页查询对象")
@Getter
@Setter
public class LogPageQuery extends BasePageQuery {

    //@Schema(description="关键字(日志内容/请求路径/请求方法/地区/浏览器/终端系统)")
    private String keywords;

    //@Schema(description="操作时间范围")
    List<String> createTime;

}
