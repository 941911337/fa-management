package cn.john.service;

import cn.john.dto.EnclosureVo;
import cn.john.model.TEnclosure;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 附件表  服务类
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
public interface ITEnclosureService extends IService<TEnclosure> {

    /**
     * 保存附件
     * @param list 附件列表
     * @param relId 关联id
     */
    void save(List<EnclosureVo> list, Long relId);

    /**
     * 获取附件列表
     * @param relId 关联id
     * @return 返回列表
     */
    List<EnclosureVo> getEnclosureByRelId(Long relId);

    /**
     * 复制列表
     * @param oldRelId 旧关联id
     * @param newRelId 新关联id
     */
    void copy(Long oldRelId,Long newRelId);

    /**
     * 删除附件
     * @param relId
     */
    void del(Long relId);

    /**
     * 获取需要备份的列表
     * @return 备份列表
     */
    List<TEnclosure> getNeedBackUp();

    /**
     * 标记已经下载
     * @param id id
     */
    void markDownLoad(Long id);

}
