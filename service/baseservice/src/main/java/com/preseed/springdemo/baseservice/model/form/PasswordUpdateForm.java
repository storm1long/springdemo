package com.preseed.springdemo.baseservice.model.form;


import lombok.Data;

/**
 * 修改密码表单
 *
 * @author Ray.Hao
 * @since 2024/8/13
 */
//@Schema(description = "修改密码表单")
@Data
public class PasswordUpdateForm {

    //@Schema(description = "原密码")
    private String oldPassword;

    //@Schema(description = "新密码")
    private String newPassword;

}
