package cn.john.dao;

import cn.john.dto.EnclosureVo;
import cn.john.model.TEnclosure;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 附件表  Mapper 接口
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
public interface TEnclosureMapper extends BaseMapper<TEnclosure> {

    /**
     * 获取关联列表
     * @param relId 关联id
     * @return 返回值
     */
    List<EnclosureVo> getEnclosureByRelId(@Param("relId") Long relId);

    /**
     * 复制记录
     * @param oldRelId 旧关联id
     * @param newRelId 新关联id
     * @return 返回值
     */
    int copy(@Param("oldRelId") Long oldRelId,@Param("newRelId") Long newRelId);

    /**
     * 批量删除
     * @param relId 关联id
     * @param ids id集合
     * @return 返回值
     */
    int delBatch(@Param("relId") Long relId,@Param("ids") List<Long> ids);

}
