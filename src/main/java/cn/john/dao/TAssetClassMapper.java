package cn.john.dao;

import cn.john.dto.ClassVo;
import cn.john.model.TAssetClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 资产类别表  Mapper 接口
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
public interface TAssetClassMapper extends BaseMapper<TAssetClass> {
    /**
     * 获取全部分类
     * @return
     */
    List<ClassVo> getAllClass();
}
