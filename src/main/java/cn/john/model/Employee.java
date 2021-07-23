package cn.john.model;

import lombok.Data;

import java.util.Date;

/**
 * @author John Yan
 */
@Data
public class Employee {
    private Long id;

    private String name;

    private Integer isDel;

    private String phone;

    private Integer revision;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

}