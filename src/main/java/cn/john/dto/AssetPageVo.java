package cn.john.dto;

import lombok.Data;

/**
 * @Author yanzhengwei
 * @Description AssetPageVo
 * @Date 2021/7/16
 **/
@Data
public class AssetPageVo extends  PageVo{

    /**
     * 资产名称
     */
    private String assetName;

    /**
     * 资产编号
     */
    private String assetCode;


    /**
     * 使用状态
     */
    private Integer useStatus;


}
