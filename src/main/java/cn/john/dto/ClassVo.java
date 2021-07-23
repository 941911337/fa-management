package cn.john.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author yanzhengwei
 * @Description ClassVo
 * @Date 2021/7/16
 **/
@Data
public class ClassVo {


    /**
     *id
     */
    private Long id;

    /**
     * 用户姓名
     */
    @NotEmpty(message = "类别名称不能为空")
    private String className;



}
