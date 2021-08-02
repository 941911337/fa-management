package cn.john.log.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author John Yan
 * @since 2021-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_fa_sys_log")
public class SysLogEntity implements Serializable, Cloneable {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模块名称
     */
    private String module;

    /**
     * 功能描述
     */
    @TableField("`function`")
    private String function;

    /**
     * 操作结果（默认值1=成功，0=失败）
     */
    @TableField("result_status")
    private Boolean resultStatus;

    /**
     * ip地址
     */
    private String ip;

    /**
     * IP实际地址 归属地
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 账号
     */
    @TableField("`account`")
    private String account;

    /**
     * 参数
     */
    private String param;

    /**
     * 删除标识 0有效1删除
     */
    @TableLogic
    @TableField("is_del")
    private Boolean isDel;

    /**
     * 乐观锁
     */
    @Version
    private Integer revision;

    /**
     * 创建人
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdTime;

    @Override
    public SysLogEntity clone() throws CloneNotSupportedException {
        return (SysLogEntity)super.clone();
    }



}
