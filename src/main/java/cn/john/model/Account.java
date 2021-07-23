package cn.john.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author John Yan
 */
@Data
public class Account implements Serializable {
    private Long id;

    private String account;

    private String password;

    private String userName;

    private Long employeeId;

    private Integer isDisable;

    private Integer isDel;

    private Integer revision;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private String remark;

    private boolean isAdmin;

    public static Account createAccount(){
        return new Account();
    }

    public  Account initId(Long id){
        this.setId(id);
        return this;
    }

}