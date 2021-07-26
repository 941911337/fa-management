package cn.john.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.john.common.Constants;
import cn.john.dao.TDeptMapper;
import cn.john.model.TDept;
import cn.john.service.ITDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 部门表  服务实现类
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
@Service
public class TDeptServiceImpl extends ServiceImpl<TDeptMapper, TDept> implements ITDeptService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveDept(TDept dept, boolean isNew){
        if (!isNew){
            TDept tDept = baseMapper.selectOne(new LambdaQueryWrapper<TDept>()
                    .eq(TDept::getDeptName,dept.getDeptName())
                    .eq(TDept::getIsDel, Constants.IsDel.NO)
                    .last(Constants.LIMIT_ONE));
            if(tDept != null){
                return tDept.getId();
            }
        }
        baseMapper.insert(dept);
        return dept.getId();
    }
}
