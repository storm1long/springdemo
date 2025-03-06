package com.preseed.springdemo.beans.base;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础分页请求对象
 *
 * @author haoxr
 * @since 2021/2/28
 */
@Data
// @Schema
public class BasePageQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // @Schema(description = "页码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private int pageNum = 1;

    // @Schema(description = "每页记录数", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private int pageSize = 10;


}
