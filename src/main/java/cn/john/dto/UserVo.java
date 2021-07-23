package cn.john.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author yanzhengwei
 * @Description ChangePasswordVo
 * @Date 2021/7/16
 **/
@Data
public class UserVo {


    /**
     *id
     */
    private Long id;

    /**
     * 用户姓名
     */
    @NotEmpty(message = "用户姓名不能为空")
    private String userName;

    /**
     * 手机号码
     */
    @NotEmpty(message = "手机号码不能为空")
    private String phone;

    /**
     * 登录账号
     */
    @NotEmpty(message = "登录账号不能为空")
    private String account;

    /**
     * 密码
     */
    @NotEmpty(message = "登录密码不能为空")
    private String password;


    /**
     * 备注
     */
    private String remark;

}
