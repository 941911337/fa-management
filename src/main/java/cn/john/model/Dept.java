package cn.john.model;

import lombok.Data;

import java.util.Date;

/**
 * @author John Yan
 */
@Data
public class Dept {
    private Long id;

    private String deptName;

    private String deptCode;

    private Integer sort;

    private Long pid;

    private Integer isDel;

    private Integer revision;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

}