package cn.john.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author John Yan
 * @Description PageVo
 * @Date 2021/7/16
 **/
@Data
public class PageVo {
    /**
     * 每页显示条数
     */
    @NotNull(message = "每页显示条数不能为空")
    private  Integer pageSize ;

    /**
     * 页码
     */
    @NotNull(message = "页数不能为空")
    private  Integer pageNum ;

}
