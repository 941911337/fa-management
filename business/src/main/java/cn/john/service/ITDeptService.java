package cn.john.service;

import cn.john.model.TDept;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 部门表  服务类
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
public interface ITDeptService extends IService<TDept> {

    /**
     * 保存部门
     * @param dept 部门
     * @param isNew 是否是新增
     * @return 部门id
     */
    Long saveDept(TDept dept, boolean isNew);
}
