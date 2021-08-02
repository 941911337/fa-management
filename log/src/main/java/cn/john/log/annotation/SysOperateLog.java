package cn.john.log.annotation;

import java.lang.annotation.*;

/**
 * 操作日志模块注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysOperateLog {
    /**
     * 模块名称
     * @return
     */
    String module();
}
