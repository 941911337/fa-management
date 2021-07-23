package cn.john.task;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Author yanzhengwei
 * @Description TaskCondition
 * @Date 2021/7/21
 **/
public class TaskCondition implements Condition {
    /**
     * dev 版本为定时任务启动条件 可根据具体情况修改
     * @param conditionContext
     * @param annotatedTypeMetadata
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return "dev".equals(conditionContext.getEnvironment().getActiveProfiles()[0]);
    }
}
