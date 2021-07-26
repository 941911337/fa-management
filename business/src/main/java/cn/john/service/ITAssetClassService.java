package cn.john.service;

import cn.john.dto.ClassPageVo;
import cn.john.dto.ClassVo;
import cn.john.model.TAssetClass;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 资产类别表  服务类
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
public interface ITAssetClassService extends IService<TAssetClass> {
    /**
     * 新增/更新分类
     * @param classVo 分类vo
     */
    void saveClass(ClassVo classVo);

    /**
     * 分页查询
     * @param classPageVo 分页vo
     * @return 分页查询结果
     */
    PageInfo getPage(ClassPageVo classPageVo);

    /**
     * 逻辑删除
     * @param id id
     */
    void del(Long id);

    /**
     * 获取全部分类
     * @return 全部资产分类
     */
    List<ClassVo> getAllClass();

    /**
     * 初始化缓存
     */
    void initCache();
}
