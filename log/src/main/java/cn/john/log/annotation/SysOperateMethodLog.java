package cn.john.log.annotation;

import java.lang.annotation.*;

/**
 * 操作日志模块方法注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysOperateMethodLog {

    /**
     * 操作名称
     *
     * @return
     */
    String method();
}
