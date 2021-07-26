package cn.john.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author John Yan
 * @Description ChangePasswordVo
 * @Date 2021/7/16
 **/
@Data
public class ChangePasswordVo {


    /**
     * 密码
     */
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;

    /**
     * 密码
     */
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
}
