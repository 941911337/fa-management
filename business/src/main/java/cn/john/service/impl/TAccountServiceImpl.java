package cn.john.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.john.common.Constants;
import cn.john.dao.TAccountMapper;
import cn.john.dto.ChangePasswordVo;
import cn.john.dto.LoginVo;
import cn.john.dto.UserPageVo;
import cn.john.dto.UserVo;
import cn.john.exception.BusinessException;
import cn.john.model.TAccount;
import cn.john.model.TEmployee;
import cn.john.service.ITAccountService;
import cn.john.service.ITEmployeeService;
import cn.john.util.SysUtil;
import cn.john.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 账号表  服务实现类
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
@Service
public class TAccountServiceImpl extends ServiceImpl<TAccountMapper, TAccount> implements ITAccountService {


    private final ITEmployeeService employeeService;

    public TAccountServiceImpl(ITEmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @Override
    public TAccount getAccount(LoginVo loginVo){
        TAccount account = baseMapper.selectOne(new LambdaQueryWrapper<TAccount>()
                .eq(TAccount::getAccount,loginVo.getUsername())
                .eq(TAccount::getPassword,loginVo.getPassword())
                .eq(TAccount::getIsDel,Constants.IsDel.NO)
                .last(Constants.LIMIT_ONE));
        if(account == null){
            throw new BusinessException("账号密码不存在") ;
        }
        return  account;
    }

    @Override
    public boolean changePassword(ChangePasswordVo changePasswordVo) {
        TAccount account = SysUtil.getUser();
        if(!changePasswordVo.getOldPassword().equals(account.getPassword())){
            throw new BusinessException("原密码错误") ;
        }
        account.setPassword(changePasswordVo.getNewPassword());
        int count = baseMapper.updateById(account);
        return count>0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(UserVo userVo){

        long count = baseMapper.selectCount(new LambdaQueryWrapper<TAccount>()
                .eq(TAccount::getAccount,userVo.getAccount())
                .eq(TAccount::getIsDel, Constants.IsDel.NO)
                .ne(userVo.getId() != null,TAccount::getId,userVo.getId())
                .last(Constants.LIMIT_ONE));
        if(count > 0){
            throw new BusinessException("账号已存在错误") ;
        }
        TEmployee employee = new TEmployee();
        employee.setName(userVo.getUserName());
        employee.setPhone(userVo.getPhone());
        TAccount account = new TAccount();
        BeanUtil.copyProperties(userVo,account);
        //更新流程
        if(userVo.getId() != null){
            TAccount accountEntity = baseMapper.selectById(userVo.getId());
            employee.setId(accountEntity.getEmployeeId());
            employeeService.updateById(employee);
            account.setId(userVo.getId());
            baseMapper.updateById(account);
        }else{
            account.setEmployeeId(employeeService.saveEmployee(employee,true));
            baseMapper.insert(account);
        }

    }

    @Override
    public PageInfo getPage(UserPageVo userPageVo) {
        PageHelper.startPage(userPageVo.getPageNum(), userPageVo.getPageSize());
        List<UserVo> list = baseMapper.getUserList(userPageVo.getUsername());
        return PageInfo.of(list);
    }

    @Override
    public void del(Long id) {
        baseMapper.deleteById(id);
        RedisUtil.del(Constants.LOGIN_CACHE_NAMESPACE+id);
    }

    @Override
    public UserVo getDetail(Long id) {
        return baseMapper.getUserDetail(id);
    }

}
