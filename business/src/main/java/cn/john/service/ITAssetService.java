package cn.john.service;

import cn.john.dto.AssetExportVo;
import cn.john.dto.AssetPageVo;
import cn.john.dto.AssetVo;
import cn.john.model.TAsset;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 资产表  服务类
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
public interface ITAssetService extends IService<TAsset> {


    /**
     * 保存资产
     * @param vo 资产vo
     */
    void saveAsset(AssetVo vo);

    /**
     * 分页查询数据
     * @param assetPageVo 分页vo
     * @return 返回值
     */
    PageInfo getPage(AssetPageVo assetPageVo);

    /**
     * 获取详情
     * @param id id
     * @return 详情vo
     */
    AssetVo detail(Long id);


    /**
     * 复制数据
     * @param id id
     */
    void copy(Long id);

    /**
     * 逻辑删除数据
     * @param id id
     */
    void del(Long id);

    /**
     * 获取全部资产列表
     * @param assetName 资产名称
     * @param assetCode 资产代码
     * @param useStatus 使用状态
     * @return 列表数据
     */
    List<AssetExportVo> getAllList(String assetName, String assetCode, Integer useStatus);
}
