package cn.john.log.service.impl;

import cn.john.log.dao.TFaSysLogMapper;
import cn.john.log.model.SysLogEntity;
import cn.john.log.service.ITFaSysLogService;
import cn.john.log.task.LogCondition;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author John Yan
 * @since 2021-08-02
 */
@Service
@Conditional(value = {LogCondition.class})
public class TFaSysLogServiceImpl extends ServiceImpl<TFaSysLogMapper, SysLogEntity> implements ITFaSysLogService {

    private final SqlSessionTemplate sqlSessionTemplate;

    public TFaSysLogServiceImpl(@Qualifier("logSqlSessionTemplate")  SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public void batchSaveLog(List<SysLogEntity> list) {
        //如果自动提交设置为true,将无法控制提交的条数，改为最后统一提交，可能导致内存溢出
        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        //不自动提交
        try {
            TFaSysLogMapper logMapper = session.getMapper(TFaSysLogMapper.class);
            for (int i = 0; i < list.size(); i++) {
                logMapper.insert(list.get(i));
                if (i % 100 == 0 || i == list.size() - 1) {
                    //手动每100条提交一次，提交后无法回滚
                    session.commit();
                    //清理缓存，防止溢出
                    session.clearCache();
                }
            }
        } catch (Exception e) {
            //没有提交的数据可以回滚
            session.rollback();
        } finally {
            session.close();
        }
    }
}
