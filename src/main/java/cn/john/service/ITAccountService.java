package cn.john.service;

import cn.hutool.core.bean.BeanUtil;
import cn.john.dto.ChangePasswordVo;
import cn.john.dto.LoginVo;
import cn.john.dto.UserPageVo;
import cn.john.dto.UserVo;
import cn.john.exception.BusinessException;
import cn.john.model.TAccount;
import cn.john.utils.SysUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 账号表  服务类
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
public interface ITAccountService extends IService<TAccount> {


    /**
     * 根据账号和密码获取用户
     * @param loginVo 登录对象
     * @return 账户信息
     */
     TAccount getAccount(LoginVo loginVo);


    /**
     * 修改当前账户的密码
     * @param changePasswordVo 修改密码vo
     * @return 修改结果
     */
    boolean changePassword(ChangePasswordVo changePasswordVo);

    /**
     * 新增/更新用户
     * @param userVo 用户vo
     */
     void saveUser(UserVo userVo);

    /**
     * 分页查询
     * @param userPageVo 用户查询分页参数
     * @return 结果返回
     */
    PageInfo getPage(UserPageVo userPageVo);

    /**
     * 逻辑删除
     * @param id id
     */
    void del(Long id);

    /**
     * 获取用户详情
     * @param id id
     * @return 返回值
     */
    UserVo getDetail(Long id);

}
