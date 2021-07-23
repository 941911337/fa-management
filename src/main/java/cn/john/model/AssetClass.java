package cn.john.model;

import lombok.Data;

import java.util.Date;

/**
 * @author John Yan
 */
@Data
public class AssetClass {
    private Long id;

    private String className;

    private Integer level;

    private Long pid;

    private Integer sort;

    private Integer isDel;

    private Integer revision;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

}