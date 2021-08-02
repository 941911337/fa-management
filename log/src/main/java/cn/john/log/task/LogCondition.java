package cn.john.log.task;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Author John Yan
 * @Description TaskCondition
 * @Date 2021/7/21
 **/
public class LogCondition implements Condition {
    /**
     * 开启日志模块则打印日志
     * @param conditionContext
     * @param annotatedTypeMetadata
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String logEnable = conditionContext.getEnvironment().getProperty("fa.log.enable");
        return "true".equals(logEnable);
    }
}
