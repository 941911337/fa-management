package cn.john.dao;

import cn.john.dto.ClassVo;
import cn.john.model.AssetClass;
import cn.john.model.AssetClassExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author John Yan
 */
public interface AssetClassMapper {
    long countByExample(AssetClassExample example);

    int deleteByExample(AssetClassExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AssetClass record);

    int insertSelective(AssetClass record);

    List<AssetClass> selectByExample(AssetClassExample example);

    AssetClass selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AssetClass record, @Param("example") AssetClassExample example);

    int updateByExample(@Param("record") AssetClass record, @Param("example") AssetClassExample example);

    int updateByPrimaryKeySelective(AssetClass record);

    int updateByPrimaryKey(AssetClass record);

    List<ClassVo>  getAllClass();
}