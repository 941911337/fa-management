package cn.john.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Author yanzhengwei
 * @Description ChangePasswordVo
 * @Date 2021/7/16
 **/
@Data
public class AssetVo {


    /**
     *id
     */
    private Long id;

    /**
     * 资产名称
     */
    @NotEmpty(message = "资产名称不能为空")
    private String name;

    /**
     * 资产编号
     */
    @NotEmpty(message = "资产编号不能为空")
    private String code;

    /**
     * 使用状态
     */
    @NotNull(message = "使用状态不能为空")
    private Integer useStatus;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 类别id
     */
    @NotNull(message = "类别Id不能为空")
    private Long classId;

    /**
     * 类别名称
     */
    private String className;


    /**
     * 规格型号
     */
    @NotEmpty(message = "规格型号不能为空")
    private String specifications;

    /**
     * 数量
     */
    @NotNull(message = "数量不能为空")
    private Integer quantity;


    /**
     * 部门
     */
    @NotEmpty(message = "使用部门不能为空")
    private String dept;

    /**
     * 部门
     */
    @NotEmpty(message = "使用人不能为空")
    private String employee;


    /**
     * 存放区域
     */
    @NotEmpty(message = "存放区域不能为空")
    private String area;

    /**
     * 购入时间
     */
    @NotNull(message = "购入时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date buyTime;

    /**
     * 购入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;


    /**
     * 附件
     */
    @Valid
    private List<EnclosureVo> enclosureVoList;

    /**
     * 备注
     */
    private String remark;

}
