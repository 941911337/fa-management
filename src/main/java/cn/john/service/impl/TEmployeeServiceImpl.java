package cn.john.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.john.common.Constants;
import cn.john.dao.TEmployeeMapper;
import cn.john.model.TEmployee;
import cn.john.service.ITEmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 员工表 服务实现类
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
@Service
public class TEmployeeServiceImpl extends ServiceImpl<TEmployeeMapper, TEmployee> implements ITEmployeeService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveEmployee(TEmployee employee,boolean isNew){
        if (!isNew){
            TEmployee tEmployee = baseMapper.selectOne(new LambdaQueryWrapper<TEmployee>()
                    .eq(TEmployee::getName,employee.getName())
                    .eq(TEmployee::getIsDel, Constants.IsDel.NO)
                    .last(Constants.LIMIT_ONE));
            if(tEmployee != null){
                return tEmployee.getId();
            }
        }
        baseMapper.insert(employee);
        return employee.getId();
    }
}
