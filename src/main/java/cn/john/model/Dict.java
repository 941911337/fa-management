package cn.john.model;

import lombok.Data;

import java.util.Date;

/**
 * @author John Yan
 */
@Data
public class Dict {
    private Long id;

    private String type;

    private String typeName;

    private String label;

    private String value;

    private Integer sort;

    private Integer isDel;

    private Integer revision;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

}