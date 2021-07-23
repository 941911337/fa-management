package cn.john.dao;

import cn.john.dto.UserVo;
import cn.john.model.Account;
import cn.john.model.AccountExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author John Yan
 */
public interface AccountMapper {
    long countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Account record);

    int insertSelective(Account record);

    List<Account> selectByExample(AccountExample example);

    Account selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);


    List<UserVo> getUserList(@Param("userName") String userName);

    UserVo getUserDetail(@Param("id") Long id);
}