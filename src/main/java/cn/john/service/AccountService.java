package cn.john.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.john.dao.AccountMapper;
import cn.john.dao.EmployeeMapper;
import cn.john.dto.ChangePasswordVo;
import cn.john.dto.LoginVo;
import cn.john.dto.UserPageVo;
import cn.john.dto.UserVo;
import cn.john.exception.BusinessException;
import cn.john.model.Account;
import cn.john.model.AccountExample;
import cn.john.model.Employee;
import cn.john.utils.SysUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author yanzhengwei
 * @Description AccountService
 * @Date 2021/7/15
 **/
@Service
public class AccountService extends BaseService<AccountMapper> {
    

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;

    /**
     * 根据账号和密码获取用户
     * @param loginVo 登录对象
     * @return 账户信息
     */
    public Account getAccount(LoginVo loginVo){
        AccountExample accountExample = new AccountExample();
        AccountExample.Criteria criteria = accountExample.createCriteria();
        criteria.andAccountEqualTo(loginVo.getUsername()).andPasswordEqualTo(loginVo.getPassword()).andIsDelEqualTo(0)
                .andIsDisableEqualTo(0);
        List<Account> list = baseMapper.selectByExample(accountExample);
        if(CollectionUtil.isEmpty(list)){
            throw new BusinessException("账号密码不存在") ;
        }
        return list.get(0);
    }

    /**
     * 修改密码
     * @param changePasswordVo
     */
    public void changePassword(ChangePasswordVo changePasswordVo){
        Account account = SysUtil.getUser();
        if(!changePasswordVo.getOldPassword().equals(account.getPassword())){
            throw new BusinessException("原密码错误") ;
        }
        account.setPassword(changePasswordVo.getNewPassword());
        updateInfo(account);
        baseMapper.updateByPrimaryKey(account);
    }

    /**
     * 新增/更新用户
     * @param userVo
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(UserVo userVo){
        AccountExample accountExample = new AccountExample();
        AccountExample.Criteria criteria = accountExample.createCriteria();
        criteria.andAccountEqualTo(userVo.getAccount()).andIsDelEqualTo(0)
                .andIsDisableEqualTo(0);
        if(userVo.getId() != null){
            criteria.andIdNotEqualTo(userVo.getId());
        }
        long count = baseMapper.countByExample(accountExample);
        if(count > 0){
            throw new BusinessException("账号已存在错误") ;
        }
        Employee employee = new Employee();
        employee.setName(userVo.getUserName());
        employee.setPhone(userVo.getPhone());
        Account account = new Account();
        BeanUtil.copyProperties(userVo,account);
        //更新流程
        if(userVo.getId() != null){
            Account accountEntity = baseMapper.selectByPrimaryKey(userVo.getId());
            employee.setId(accountEntity.getEmployeeId());
            updateInfo(employee);
            employeeMapper.updateByPrimaryKeySelective(employee);
            account.setId(userVo.getId());
            updateInfo(account);
            baseMapper.updateByPrimaryKeySelective(account);
        }else{
            account.setEmployeeId(employeeService.saveEmployee(employee,true));
            createInfo(account);
            baseMapper.insertSelective(account);
        }

    }


    public PageInfo getPage(UserPageVo userPageVo){
        PageHelper.startPage(userPageVo.getPageNo(), userPageVo.getPageSize());
        List<UserVo> list = baseMapper.getUserList(userPageVo.getUsername());
        return PageInfo.of(list);

    }

    public void del(Long id){
        if(SysUtil.getUser().getId().equals(id)){
            throw new BusinessException("禁止删除自身用户");
        }
        Account account = new Account();
        account.setId(id);
        account.setIsDel(1);
        baseMapper.updateByPrimaryKeySelective(account);
    }

    public UserVo getDetail(Long id){
        return baseMapper.getUserDetail(id);
    }



}
