package cn.john.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author yanzhengwei
 * @Description ChangePasswordVo
 * @Date 2021/7/16
 **/
@Data
public class EnclosureVo {


    /**
     *id
     */
    private Long id;

    /**
     * 资产名称
     */
    @NotEmpty(message = "附件名称不能为空")
    private String name;

    /**
     * 资产编号
     */
    @NotEmpty(message = "附件路径不能为空")
    private String path;



}
