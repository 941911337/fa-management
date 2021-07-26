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
 * 账号表 
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_account")
public class TAccount implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    @TableField("`account`")
    private String account;

    /**
     * 密码
     */
    @TableField("`password`")
    private String password;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 员工id
     */
    @TableField("employee_id")
    private Long employeeId;

    /**
     * 禁用标识 0有效1禁用
     */
    @TableField("is_disable")
    private Integer isDisable;

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

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private boolean admin;


    public static TAccount createAccount(){
        return new TAccount();
    }

    public  TAccount initId(Long id){
        this.setId(id);
        return this;
    }
}
