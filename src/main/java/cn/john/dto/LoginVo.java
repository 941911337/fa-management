package cn.john.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author John Yan
 * @Description LoginVo
 * @Date 2021/7/16
 **/
@Data
public class LoginVo {

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    private String password;
}
