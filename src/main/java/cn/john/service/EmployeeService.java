package cn.john.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.john.dao.EmployeeMapper;
import cn.john.model.Employee;
import cn.john.model.EmployeeExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author yanzhengwei
 * @Description EmployeeService
 * @Date 2021/7/18
 **/
@Slf4j
@Service
public class EmployeeService extends  BaseService<EmployeeMapper> {

    @Transactional(rollbackFor = Exception.class)
    public Long saveEmployee(Employee employee,boolean isNew){
        if (!isNew){
            EmployeeExample employeeExample = new EmployeeExample();
            EmployeeExample.Criteria criteria = employeeExample.createCriteria() ;
            criteria.andNameEqualTo(employee.getName()).andIsDelEqualTo(0);
            List<Employee> list = baseMapper.selectByExample(employeeExample);
            if(CollectionUtil.isNotEmpty(list)){
                return list.get(0).getId();
            }
        }
        createInfo(employee);
        baseMapper.insertSelective(employee);
        return employee.getId();
    }
}
