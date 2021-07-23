package cn.john.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.john.dao.DeptMapper;
import cn.john.model.Dept;
import cn.john.model.DeptExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author yanzhengwei
 * @Description DeptService
 * @Date 2021/7/18
 **/
@Slf4j
@Service
public class DeptService extends  BaseService<DeptMapper> {

    @Transactional(rollbackFor = Exception.class)
    public Long saveDept(Dept dept, boolean isNew){
        if (!isNew){
            DeptExample deptExample = new DeptExample();
            DeptExample.Criteria criteria = deptExample.createCriteria() ;
            criteria.andDeptNameEqualTo(dept.getDeptName()).andIsDelEqualTo(0);
            List<Dept> list = baseMapper.selectByExample(deptExample);
            if(CollectionUtil.isNotEmpty(list)){
                return list.get(0).getId();
            }
        }
        createInfo(dept);
        baseMapper.insertSelective(dept);
        return dept.getId();
    }
}
