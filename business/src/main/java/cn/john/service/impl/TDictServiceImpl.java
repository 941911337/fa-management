package cn.john.service.impl;

import cn.john.common.Constants;
import cn.john.dao.TDictMapper;
import cn.john.model.TDict;
import cn.john.service.ITDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
@Service
public class TDictServiceImpl extends ServiceImpl<TDictMapper, TDict> implements ITDictService {

    @Override
    public Map<String, List<TDict>> getAllDict(){
        List<TDict> list = baseMapper.selectList(new LambdaQueryWrapper<TDict>()
                .eq(TDict::getIsDel, Constants.IsDel.NO)
                .orderByAsc(TDict::getSort));
        return list.stream().collect(Collectors.groupingBy(TDict::getType));
    }
}
