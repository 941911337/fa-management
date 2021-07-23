package cn.john.model;

import lombok.Data;

import java.util.Date;

/**
 * @author John Yan
 */
@Data
public class Asset {
    private Long id;

    private String name;

    private String code;

    private Integer useStatus;

    private Long classId;

    private String specifications;

    private Integer quantity;

    private String dept;

    private String employee;

    private String area;

    private Date buyTime;

    private String remark;

    private Integer isDel;

    private Integer revision;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

}