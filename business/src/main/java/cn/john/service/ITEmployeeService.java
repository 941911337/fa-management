package cn.john.service;

import cn.john.model.TEmployee;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 员工表 服务类
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
public interface ITEmployeeService extends IService<TEmployee> {

    /**
     * 保存雇员
     * @param employee 雇员对象
     * @param isNew 是否是新雇员
     * @return 雇员id
     */
     Long saveEmployee(TEmployee employee,boolean isNew);
}
