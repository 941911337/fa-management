package cn.john.log.service;

import cn.john.log.model.SysLogEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author John Yan
 * @since 2021-08-02
 */
public interface ITFaSysLogService extends IService<SysLogEntity> {
    /**
     * 日志批量存储
     * @param list
     */
    void batchSaveLog(List<SysLogEntity> list);
}
