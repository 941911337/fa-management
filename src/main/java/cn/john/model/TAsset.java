package cn.john.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资产表 
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_asset")
public class TAsset implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 资产名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 资产编号
     */
    @TableField("`code`")
    private String code;

    /**
     * 使用状态
     */
    @TableField("use_status")
    private Integer useStatus;

    /**
     * 类别id
     */
    @TableField("class_id")
    private Long classId;

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
     * 使用人
     */
    private String employee;

    /**
     * 存放区域
     */
    private String area;

    /**
     * 购入时间
     */
    @TableField("buy_time")
    private LocalDateTime buyTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标识 0有效1删除
     */
    @TableLogic
    @TableField("is_del")
    private Integer isDel;

    /**
     * 乐观锁
     */
    @Version
    private Integer revision;

    /**
     * 创建人
     */
    @TableField(value = "created_by",fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    @TableField(value ="updated_by", fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedTime;


}
