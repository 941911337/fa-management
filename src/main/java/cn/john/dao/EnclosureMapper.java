package cn.john.dao;

import cn.john.dto.EnclosureVo;
import cn.john.model.Enclosure;
import cn.john.model.EnclosureExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author John Yan
 */
public interface EnclosureMapper {
    long countByExample(EnclosureExample example);

    int deleteByExample(EnclosureExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Enclosure record);

    int insertSelective(Enclosure record);

    List<Enclosure> selectByExampleWithBLOBs(EnclosureExample example);

    List<Enclosure> selectByExample(EnclosureExample example);

    Enclosure selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Enclosure record, @Param("example") EnclosureExample example);

    int updateByExampleWithBLOBs(@Param("record") Enclosure record, @Param("example") EnclosureExample example);

    int updateByExample(@Param("record") Enclosure record, @Param("example") EnclosureExample example);

    int updateByPrimaryKeySelective(Enclosure record);

    int updateByPrimaryKeyWithBLOBs(Enclosure record);

    int updateByPrimaryKey(Enclosure record);

    List<EnclosureVo> getEnclosureByRelId(@Param("relId") Long relId);

    int copy(@Param("oldRelId") Long oldRelId,@Param("newRelId") Long newRelId);

    int delBatch(@Param("relId") Long relId,@Param("ids") List<Long> ids);

}