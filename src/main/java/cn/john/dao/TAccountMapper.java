package cn.john.dao;

import cn.john.dto.UserVo;
import cn.john.model.TAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 账号表  Mapper 接口
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
public interface TAccountMapper extends BaseMapper<TAccount> {
    /**
     * 根据用户名称查询
     * @param userName 根据用户名称查询
     * @return 结果
     */
    List<UserVo> getUserList(@Param("userName") String userName);

    /**
     * 根据用户id查询用户
     * @param id id
     * @return 返回值
     */
    UserVo getUserDetail(@Param("id") Long id);
}
