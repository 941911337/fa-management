package cn.john.aspect;

import cn.john.utils.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * @Author John Yan
 * @Description ControllerAspect
 * @Date 2021/7/23
 **/
@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Pointcut("execution(public * cn.john.controller.*.*(..))")
    public void controller(){
    }


    /**
     * 删除ThreadLocal 放在后置处理
     * @param jp
     */
    @After("controller()")
    public void after(JoinPoint jp){
        log.info("调用removeThreadUser");
        SysUtil.removeThreadUser();
    }

    /**
     * 参数校验放在前置处理
     * @param joinPoint
     */
    @Before("controller()")
    public void before(JoinPoint joinPoint){
        log.info("参数校验开启");
        for (Object arg : joinPoint.getArgs()) {
            if( arg instanceof BindingResult){
                SysUtil.paramValid((BindingResult) arg);
            }
        }
    }

}
