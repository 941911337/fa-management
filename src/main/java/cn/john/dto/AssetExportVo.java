package cn.john.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Author John Yan
 * @Description AssetExportVo
 * @Date 2021/7/16
 **/
@Data
public class AssetExportVo {


    /**
     * 资产名称
     */
    private String name;

    /**
     * 资产编号
     */
    private String code;


    /**
     * 状态名称
     */
    private String statusName;


    /**
     * 类别名称
     */
    private String className;


    /**
     * 规格型号
     */
    private String specifications;

    /**
     * 数量
     */
    private Integer quantity;


    /**
     * 部门
     */
    private String dept;

    /**
     * 部门
     */
    private String employee;


    /**
     * 存放区域
     */
    private String area;

    /**
     * 购入时间
     */
    private String buyerTime;

    /**
     * 购入时间
     */
    private String createTime;


    /**
     * 备注
     */
    private String remark;

}
